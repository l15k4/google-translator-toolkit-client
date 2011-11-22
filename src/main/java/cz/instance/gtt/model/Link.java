package cz.instance.gtt.model;

import com.google.api.client.util.Key;

import cz.instance.gtt.RequestCallback;
import cz.instance.gtt.utils.PrintUtils;

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
	
	@Override
	public String toString()  {
		String xml = RequestCallback.GTT_DICTIONARY.toStringOf("link", this);
		return PrintUtils.prettyFormat(xml);
	}
}
