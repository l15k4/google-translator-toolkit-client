package cz.instance.gtt.model;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.Key;

import cz.instance.gtt.RequestCallback;
import cz.instance.gtt.utils.PrintUtils;

public class Entry {

	public Entry() {
	}

	public Entry(String title) {
		this.title = title;
	}

	@Key("id")
	public String id;

	@Key("updated")
	public String updated;

	@Key("app:edited")
	public String edited;

	@Key("title")
	public String title;

	@Key("link")
	public List<Link> links = new ArrayList<Link>();

	public String getIdString() {
		int lastSlash = id.lastIndexOf("/");
		return id.substring(lastSlash + 1);
	}

	@Override
	public String toString()  {
		String xml = RequestCallback.GTT_DICTIONARY.toStringOf("entry", this);
		return PrintUtils.prettyFormat(xml);
	}
	
}
