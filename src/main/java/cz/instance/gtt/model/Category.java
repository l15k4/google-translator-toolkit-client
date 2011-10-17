package cz.instance.gtt.model;

import com.google.api.client.util.Key;

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
	
}
