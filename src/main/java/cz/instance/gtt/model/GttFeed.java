package cz.instance.gtt.model;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.Key;
import com.google.api.client.xml.GenericXml;

public class GttFeed<E> extends GenericXml implements Feed<E>  {

	@Key("title")
	public String title;

	@Key("updated")
	public String updated;

	@Key("id")
	public String id;

	@Key("openSearch:startIndex")
	public int startIndex;

	@Key("openSearch:totalResults")
	public int totalResults;

	@Key("openSearch:itemsPerPage")
	public int itemsPerPage;

	@Key("link")
	public List<Link> links = new ArrayList<Link>();

	@Key("entry")
	public List<E> entries = new ArrayList<E>();

	public List<E> getFeedEntries() {
		return entries;
	}
	
	public void setFeedEntries(List<E> e) {
		entries = e;
	}

	public E getEntry(int index) {
		return entries.get(index);
	}

	public void addEntry(E e) {
		entries.add(e);
	}
	
	
}
