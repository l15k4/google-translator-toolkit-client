package cz.instance.gtt.model;

import com.google.api.client.util.Key;

import cz.instance.gtt.RequestCallback;
import cz.instance.gtt.utils.PrintUtils;

public class TmEntry extends Entry {

	public TmEntry() {
	}

	public TmEntry(String title) {
		super(title);
	}

	@Key("@gd:etag")
	public String etag;
	
	@Key("gtt:scope")
	public String scope;
	
	@Override
	public String toString()  {
		String xml = RequestCallback.GTT_DICTIONARY.toStringOf("tmEntry", this);
		return PrintUtils.prettyFormat(xml);
	}
}
