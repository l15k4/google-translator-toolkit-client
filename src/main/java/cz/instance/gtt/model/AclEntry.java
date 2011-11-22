package cz.instance.gtt.model;

import com.google.api.client.util.Key;

import cz.instance.gtt.RequestCallback;
import cz.instance.gtt.utils.PrintUtils;

public class AclEntry extends Entry {

	public AclEntry() {
	}

	public AclEntry(Role role, Scope scope, Category cat) {
		this.role = role;
		this.scope = scope;
		this.category = cat;
	}

	@Key("gAcl:role")
	public Role role;

	@Key("gAcl:scope")
	public Scope scope;
	
	@Key("category")
	public Category category; 
	
	@Override
	public String toString()  {
		String xml = RequestCallback.GTT_DICTIONARY.toStringOf("aclEntry", this);
		return PrintUtils.prettyFormat(xml);
	}
	
}
