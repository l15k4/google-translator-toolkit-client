package cz.instance.gtt.model;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.Key;

public class Glossary {

	@Key("link")
	public List<Link> links = new ArrayList<Link>(); // for now just ony link is supported (1 TM for Document)
	
}
