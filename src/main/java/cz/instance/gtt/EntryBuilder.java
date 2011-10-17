package cz.instance.gtt;

import cz.instance.gtt.model.AclEntry;
import cz.instance.gtt.model.Category;
import cz.instance.gtt.model.Content;
import cz.instance.gtt.model.DocumentSource;
import cz.instance.gtt.model.Glossary;
import cz.instance.gtt.model.GttEntry;
import cz.instance.gtt.model.Link;
import cz.instance.gtt.model.Role;
import cz.instance.gtt.model.Scope;
import cz.instance.gtt.model.TranslationMemory;

public class EntryBuilder {

	private static final String CAT_SCHEME = "http://schemas.google.com/g/2005#kind";
	private static final String CAT_TERM = "http://schemas.google.com/acl/2007#accessRule";
	
	public GttEntry buildGttEntry(String mediaType, String title, String sourceLang, String targetLang) {
		Content content = new Content(mediaType);
		GttEntry entry = new GttEntry(title, content, sourceLang, targetLang);
		return entry;
	}
	
	public GttEntry buildWebPageEntry(String url, String title, String sourceLang, String targetLang) {
		DocumentSource ds = new DocumentSource("html", url);
		GttEntry entry = new GttEntry(title, ds, sourceLang, targetLang);
		return entry;
	}
	
	public GttEntry buildGttEntryWithGlossary(String idUrl, String mediaType, String fileName, String sourceLang, String targetLang) {
		GttEntry entry = buildGttEntry(mediaType, fileName, sourceLang, targetLang);
		Glossary glossary = new Glossary();
		glossary.links.add(new Link(idUrl));
		entry.glossary = glossary;
		return entry;
	}
	
	public GttEntry buildGttEntryWithTM(String idUrl, String mediaType, String fileName, String sourceLang, String targetLang) {
		GttEntry entry = buildGttEntry(mediaType, fileName, sourceLang, targetLang);
		TranslationMemory tm = new TranslationMemory();
		tm.links.add(new Link(idUrl));
		entry.translationMemory = tm;
		return entry;
	}

	public AclEntry buildAclEntry(String scopeType, String scopeValue, String roleValue) {
		
		Category category = new Category(CAT_SCHEME, CAT_TERM);
		Scope scope = new Scope(scopeType, scopeValue);
		Role role = new Role(roleValue);
		AclEntry aclEntry = new AclEntry(role, scope, category);
		return aclEntry;
	}
	
	public GttEntry updateEntry(GttEntry targetEntry, String title) {
		targetEntry.title = title;
		return targetEntry;
	}
	
}
