package cz.instance.gtt;

import java.io.IOException;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.xml.XmlNamespaceDictionary;

public interface RequestCallback {

	public static final XmlNamespaceDictionary GTT_DICTIONARY = new XmlNamespaceDictionary().set("", "http://www.w3.org/2005/Atom")
			.set("app", "http://www.w3.org/2007/app").set("gtt", "http://schemas.google.com/gtt/2009/11").set("gAcl", "http://schemas.google.com/acl/2007");
	
	public HttpRequest build(HttpRequestFactory requestFactory) throws IOException;

}