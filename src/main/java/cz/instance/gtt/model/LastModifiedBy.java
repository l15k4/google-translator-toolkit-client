package cz.instance.gtt.model;

import com.google.api.client.util.Key;

import cz.instance.gtt.RequestCallback;
import cz.instance.gtt.utils.PrintUtils;

public class LastModifiedBy {

	@Key("name")
	public String name;
	
	@Key("email")
	public String email;
	
	@Override
	public String toString()  {
		String xml = RequestCallback.GTT_DICTIONARY.toStringOf("lastModifiedBy", this);
		return PrintUtils.prettyFormat(xml);
	}
}
