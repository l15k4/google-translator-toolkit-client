package cz.instance.gtt.impl;

import java.io.IOException;

import com.google.api.client.http.HttpMethod;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;

import cz.instance.gtt.RequestCallback;
import cz.instance.gtt.model.GttUrl;

public class RequestCallbackImpl implements RequestCallback {

	public GttUrl gttUrl;
	public HttpMethod method;

	public RequestCallbackImpl(GttUrl gttUrl, HttpMethod method) {
		this.gttUrl = gttUrl;
		this.method = method;
	}

	public RequestCallbackImpl(GttUrl gttUrl) {
		this.gttUrl = gttUrl;
	}

	@Override
	public HttpRequest build(HttpRequestFactory requestFactory) throws IOException {

		if (method == null || (method != HttpMethod.GET && method != HttpMethod.DELETE))
		 	throw new Error("method is : " + method + " - it must be either GET or DELETE");
	
		HttpRequest request = null;

		switch (method) {
		case GET:
			request = requestFactory.buildGetRequest(gttUrl);
			break;

		case DELETE:
			request = requestFactory.buildDeleteRequest(gttUrl);
			break;

		default:
			throw new Error("Should never happen");
		}
		return request;
	}
}
