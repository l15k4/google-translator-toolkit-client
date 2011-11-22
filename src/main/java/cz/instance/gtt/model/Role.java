package cz.instance.gtt.model;

import com.google.api.client.util.Key;

import cz.instance.gtt.RequestCallback;
import cz.instance.gtt.utils.PrintUtils;

public class Role {

	public Role() {
	}

	public Role(String value) {
		this.value = value;
	}

	@Key("@value")
	public String value;
	
	@Override
	public String toString()  {
		String xml = RequestCallback.GTT_DICTIONARY.toStringOf("role", this);
		return PrintUtils.prettyFormat(xml);
	}
}
