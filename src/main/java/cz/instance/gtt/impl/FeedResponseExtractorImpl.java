package cz.instance.gtt.impl;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.xml.atom.AtomFeedParser;

import cz.instance.gtt.RequestCallback;
import cz.instance.gtt.ResponseExtractor;
import cz.instance.gtt.model.Feed;
import cz.instance.gtt.utils.GenericType;

public class FeedResponseExtractorImpl<F extends Feed<E>, E> implements ResponseExtractor<F> {

	GenericType<?> type;

	public FeedResponseExtractorImpl(GenericType<?> typeLiteral) {
		type = typeLiteral;
	}

	public F extract(HttpResponse response) throws IOException {

		if (type.isParameterized())
			try {
				return parseToFeed(response, (Class<F>) type.getClazz(), (Class<E>) type.getRawType());
			} catch (XmlPullParserException e) {
				throw new IOException("Parser exception", e);
			}
		else 
			throw new RuntimeException();
	}

	public F parseToFeed(HttpResponse response, Class<F> feedClass, Class<E> entryClass) throws IOException, XmlPullParserException {
		AtomFeedParser<F, E> parser = AtomFeedParser.create(response, RequestCallback.GTT_DICTIONARY, feedClass, entryClass);
		F feed = parser.parseFeed();
		E entry;
		while ((entry = parser.parseNextEntry()) != null) {
			feed.addEntry(entry);
		}
		return feed;
	}

}
