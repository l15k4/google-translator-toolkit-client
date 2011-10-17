package cz.instance.gtt.model;

import com.google.api.client.util.Key;

public class Scope {

	public Scope() {
	}

	public Scope(String type, String value) {
		this.type = type;
		this.value = value;
	}

	@Key("@type")
	public String type;

	@Key("@value")
	public String value;
	
}
