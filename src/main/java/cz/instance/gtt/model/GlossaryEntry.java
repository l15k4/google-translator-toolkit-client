package cz.instance.gtt.model;

import com.google.api.client.util.Key;

public class GlossaryEntry extends Entry {

	public GlossaryEntry() {
	}

	public GlossaryEntry(String title) {
		super(title);
	}

	@Key("@gd:etag")
	public String etag;

		
}
