package cz.instance.gtt.model;

import com.google.api.client.util.Key;

import cz.instance.gtt.RequestCallback;
import cz.instance.gtt.utils.PrintUtils;

public class Category {

	public Category() {
	}

	public Category(String scheme, String term) {
		this.scheme = scheme;
		this.term = term;
	}

	@Key("@scheme")
	public String scheme;
	
	@Key("@term")
	public String term;
	
	@Key("@label")
	public String label;
	
	@Override
	public String toString()  {
		String xml = RequestCallback.GTT_DICTIONARY.toStringOf("category", this);
		return PrintUtils.prettyFormat(xml);
	}
}
