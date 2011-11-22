package cz.instance.gtt.model;

import com.google.api.client.util.Key;

import cz.instance.gtt.RequestCallback;
import cz.instance.gtt.utils.PrintUtils;

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
	
	@Override
	public String toString()  {
		String xml = RequestCallback.GTT_DICTIONARY.toStringOf("content", this);
		return PrintUtils.prettyFormat(xml);
	}
}
