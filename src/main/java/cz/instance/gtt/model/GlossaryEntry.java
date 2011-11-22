package cz.instance.gtt.model;

import com.google.api.client.util.Key;

import cz.instance.gtt.RequestCallback;
import cz.instance.gtt.utils.PrintUtils;

public class GlossaryEntry extends Entry {

	public GlossaryEntry() {
	}

	public GlossaryEntry(String title) {
		super(title);
	}

	@Key("@gd:etag")
	public String etag;

	@Override
	public String toString()  {
		String xml = RequestCallback.GTT_DICTIONARY.toStringOf("glosaryEntry", this);
		return PrintUtils.prettyFormat(xml);
	}
}
