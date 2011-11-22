package cz.instance.gtt.model;

import java.util.List;

import com.google.api.client.util.Key;

import cz.instance.gtt.RequestCallback;
import cz.instance.gtt.utils.PrintUtils;

public class TranslationMemory {

	@Key("link")
	public List<Link> links; // for now just ony link is supported (1 TM for Document)
	
	@Override
	public String toString()  {
		String xml = RequestCallback.GTT_DICTIONARY.toStringOf("translationMemory", this);
		return PrintUtils.prettyFormat(xml);
	}
}
