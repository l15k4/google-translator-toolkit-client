package cz.instance.gtt.model;

import com.google.api.client.util.Key;

public class Content {
	
	public Content() {
	}

	public Content(String type) {
		this.type = type;
	}

	@Key("@type")
	public String type;
	
	@Key("@src")
	public String src;
}
