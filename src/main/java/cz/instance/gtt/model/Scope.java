package cz.instance.gtt.model;

import com.google.api.client.util.Key;

import cz.instance.gtt.RequestCallback;
import cz.instance.gtt.utils.PrintUtils;

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
	
	@Override
	public String toString()  {
		String xml = RequestCallback.GTT_DICTIONARY.toStringOf("scope", this);
		return PrintUtils.prettyFormat(xml);
	}
}
