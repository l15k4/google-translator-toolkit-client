package cz.instance.gtt.model;

import java.util.List;

public interface Feed<E> {

	public List<E> getFeedEntries();
	
	public void setFeedEntries(List<E> e);
	
	public E getEntry(int index);
	
	public void addEntry(E e);
	
}
