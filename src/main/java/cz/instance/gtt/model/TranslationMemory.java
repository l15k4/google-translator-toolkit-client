package cz.instance.gtt.model;

import java.util.List;

import com.google.api.client.util.Key;

public class TranslationMemory {

	@Key("link")
	public List<Link> links; // for now just ony link is supported (1 TM for Document)
}
