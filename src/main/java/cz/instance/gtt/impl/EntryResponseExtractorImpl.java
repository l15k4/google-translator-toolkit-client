package cz.instance.gtt.impl;

import java.io.IOException;

import com.google.api.client.http.HttpResponse;

import cz.instance.gtt.ResponseExtractor;
import cz.instance.gtt.model.Entry;
import cz.instance.gtt.utils.GenericType;

public class EntryResponseExtractorImpl<E extends Entry> implements ResponseExtractor<E> {

	GenericType<?> type;

	public EntryResponseExtractorImpl(GenericType<?> typeLiteral) {
		type = typeLiteral;
	}

	public E extract(HttpResponse response) throws IOException {
		
		if(Entry.class.isAssignableFrom((Class<E>) type.getClazz()))
			return parseToEntry(response, (Class<E>) type.getClazz());
		else 
			throw new RuntimeException();
	}

	public E parseToEntry(HttpResponse response, Class<E> clazz) throws IOException {
		return response.parseAs(clazz);
	}
}
