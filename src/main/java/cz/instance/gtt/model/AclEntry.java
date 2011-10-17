package cz.instance.gtt.model;

import com.google.api.client.util.Key;

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
	
}
