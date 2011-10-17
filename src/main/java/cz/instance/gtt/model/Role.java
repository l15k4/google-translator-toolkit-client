package cz.instance.gtt.model;

import com.google.api.client.util.Key;

public class Role {

	public Role() {
	}

	public Role(String value) {
		this.value = value;
	}

	@Key("@value")
	public String value;
}
