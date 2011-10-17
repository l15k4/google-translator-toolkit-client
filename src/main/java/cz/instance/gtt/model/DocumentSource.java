package cz.instance.gtt.model;

import com.google.api.client.util.Key;

public class DocumentSource {

	public DocumentSource() {
	}

	public DocumentSource(String type, String url) {
		this.type = type;
		this.url = url;
	}

	@Key("@type")
	public String type;

	@Key("@url")
	public String url;
	
}
