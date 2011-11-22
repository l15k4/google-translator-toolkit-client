package cz.instance.gtt.model;

import com.google.api.client.util.Key;

import cz.instance.gtt.RequestCallback;
import cz.instance.gtt.utils.PrintUtils;

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
	
	@Override
	public String toString()  {
		String xml = RequestCallback.GTT_DICTIONARY.toStringOf("content", this);
		return PrintUtils.prettyFormat(xml);
	}
}
