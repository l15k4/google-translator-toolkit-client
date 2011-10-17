package cz.instance.gtt;

import java.io.IOException;

import com.google.api.client.http.HttpResponse;

public interface ResponseExtractor<T> {

	public T extract(HttpResponse response) throws IOException;
	
}