package cz.instance.gtt.model;

import com.google.api.client.util.Key;

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
	
}
