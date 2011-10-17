package cz.instance.gtt.impl;

import java.io.IOException;
import java.io.InputStream;

import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpMethod;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.MultipartRelatedContent;
import com.google.api.client.http.xml.atom.AtomContent;

import cz.instance.gtt.model.Content;
import cz.instance.gtt.model.Entry;
import cz.instance.gtt.model.GttUrl;

public class EntityRequestCallbackImpl<E extends Entry> extends RequestCallbackImpl {

	public E entity;
	public InputStream is;
	
	public EntityRequestCallbackImpl(GttUrl gttUrl, HttpMethod method, E entity, InputStream inputStream) {
		super(gttUrl, method);
		this.entity = entity;
		this.is = inputStream;
	}

	public EntityRequestCallbackImpl(GttUrl gttUrl, HttpMethod method, E entity) {
		super(gttUrl, method);
		this.entity = entity;
	}
	
	public HttpRequest build(HttpRequestFactory requestFactory) throws IOException {

		if (method == null || (method != HttpMethod.POST && method != HttpMethod.PUT))
			throw new Error("method is : " + method + " - it must be either POST or PUT");
		
		HttpRequest request = null;
		
		switch (method) {
			
		case PUT:
			request = requestFactory.buildPutRequest(gttUrl, buildContent(entity));
			break;

		case POST:
			if(is == null)
				request = requestFactory.buildPostRequest(gttUrl, buildContent(entity));
			else
				request = requestFactory.buildPostRequest(gttUrl, buildMultiPartContent(entity, is));
			break;

		default:
			throw new Error("Should never happen");
		}
		return request;
	}

	public AtomContent buildContent(E inputEntry) {
		return AtomContent.forEntry(GTT_DICTIONARY, inputEntry);
	}

	public HttpContent buildMultiPartContent(E inputEntry, InputStream is) {
		AtomContent ac = buildContent(inputEntry);	
		Content content = null;
		try {
			content = (Content) inputEntry.getClass().getField("content").get(inputEntry);
		} catch (Exception e) {
			throw new RuntimeException("Input entry for a multipart request must have content field");
		}
		InputStreamContent isc = new InputStreamContent(content.type, is);
		return new MultipartRelatedContent(isc, ac);
	}
	
}
