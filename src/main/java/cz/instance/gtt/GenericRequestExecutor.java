package cz.instance.gtt;

import java.io.IOException;
import java.io.InputStream;

import com.google.api.client.http.HttpMethod;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;

import cz.instance.gtt.impl.EntityRequestCallbackImpl;
import cz.instance.gtt.impl.EntryResponseExtractorImpl;
import cz.instance.gtt.impl.FeedResponseExtractorImpl;
import cz.instance.gtt.impl.RequestCallbackImpl;
import cz.instance.gtt.model.AclEntry;
import cz.instance.gtt.model.Entry;
import cz.instance.gtt.model.Feed;
import cz.instance.gtt.model.Glossary;
import cz.instance.gtt.model.GlossaryEntry;
import cz.instance.gtt.model.GttEntry;
import cz.instance.gtt.model.GttFeed;
import cz.instance.gtt.model.GttUrl;
import cz.instance.gtt.model.TmEntry;
import cz.instance.gtt.utils.GenericType;

public class GenericRequestExecutor {

	private HttpRequestFactory requestFactory;

	public GenericRequestExecutor(HttpRequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	private final GenericType<GttFeed<GttEntry>> gttEntryFeed = new GenericType<GttFeed<GttEntry>>(){};
	private final GenericType<GttFeed<AclEntry>> aclEntryFeed = new GenericType<GttFeed<AclEntry>>(){};
	private final GenericType<GttFeed<GlossaryEntry>> glossaryEntryFeed = new GenericType<GttFeed<GlossaryEntry>>(){};
	private final GenericType<GttFeed<TmEntry>> tmEntryFeed = new GenericType<GttFeed<TmEntry>>(){};
	private final GenericType<GttEntry> gttEntry = new GenericType<GttEntry>(){};
	private final GenericType<AclEntry> aclEntry = new GenericType<AclEntry>(){};
	private final GenericType<GttEntry> tmEntry = new GenericType<GttEntry>(){};
	private final GenericType<GlossaryEntry> glossaryEntry = new GenericType<GlossaryEntry>(){};

	public <F extends Feed<E>, E extends Entry> F toFeed(GttUrl url, HttpMethod method, Class<E> type) throws IOException {
		RequestCallback rqb = new RequestCallbackImpl(url, method);
		return execute(rqb, new FeedResponseExtractorImpl<F, E>(resolve(type, true)));
	}

	public <E extends Entry> E toEntry(GttUrl url, HttpMethod method, Class<E> type) throws IOException {
		RequestCallback rqb = new RequestCallbackImpl(url, method);
		return execute(rqb, new EntryResponseExtractorImpl<E>(resolve(type, false)));
	}

	public <E extends Entry> E toEntry(GttUrl url, HttpMethod method, Class<E> type, E entity, InputStream is) throws IOException {
		RequestCallback rqb = new EntityRequestCallbackImpl<E>(url, method, entity, is);
		return execute(rqb, new EntryResponseExtractorImpl<E>(resolve(type, false)));
	}

	public <E extends Entry> E toEntry(GttUrl url, HttpMethod method, Class<E> type, E entity) throws IOException {
		RequestCallback rqb = new EntityRequestCallbackImpl<E>(url, method, entity);
		return execute(rqb, new EntryResponseExtractorImpl<E>(resolve(type, false)));
	}

	public Boolean toBoolean(GttUrl url, HttpMethod method) throws IOException {
		RequestCallback rqb = new RequestCallbackImpl(url, method);
		return execute(rqb, new ResponseExtractor<Boolean>() {
			public Boolean extract(HttpResponse response) throws IOException {
				int statusCode = response.getStatusCode();
				return statusCode == 200 ? true : false;
			}
		});
	}

	public InputStream toStream(GttUrl url, HttpMethod method) throws IOException {
		RequestCallback rqb = new RequestCallbackImpl(url, method);
		return execute(rqb, new ResponseExtractor<InputStream>() {
			public InputStream extract(HttpResponse response) throws IOException {
				return response.getContent();
			}
		});
	}

	public <T> T execute(RequestCallback reqCb, ResponseExtractor<T> respExtr) throws IOException {
		HttpRequest request = reqCb.build(requestFactory);
		return respExtr.extract(request.execute());
	}

	public <E extends Entry> GenericType resolve(Class<E> entry, boolean feed) {
		if (entry != null) {
			if (entry.isAssignableFrom(GttEntry.class))
				return feed ? gttEntryFeed : gttEntry;
			else if (entry.isAssignableFrom(AclEntry.class))
				return feed ? aclEntryFeed : aclEntry;
			else if (entry.isAssignableFrom(Glossary.class))
				return feed ? glossaryEntryFeed : glossaryEntry;
			else if (entry.isAssignableFrom(TmEntry.class))
				return feed ? tmEntryFeed : tmEntry;
			else
				throw new RuntimeException("Should never happen ! " + entry.getName() + " must be assignable from one of Entry's impl");
		} else {
			throw new RuntimeException("entry must not be null !");
		}
	}
}
