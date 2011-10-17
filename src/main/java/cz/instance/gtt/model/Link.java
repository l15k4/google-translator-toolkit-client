package cz.instance.gtt.model;

import com.google.api.client.util.Key;

public class Link {

	public Link() {
	}

	public Link(String href) {
		this.href = href;
	}

	@Key("@rel")
	public String rel;

	@Key("@type")
	public String type;

	@Key("@href")
	public String href;
}
