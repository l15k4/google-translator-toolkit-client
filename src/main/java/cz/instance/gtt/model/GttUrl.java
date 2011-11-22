package cz.instance.gtt.model;

import com.google.api.client.googleapis.GoogleUrl;
import com.google.api.client.util.Key;

import cz.instance.gtt.RequestCallback;
import cz.instance.gtt.utils.PrintUtils;

public class GttUrl extends GoogleUrl {

	private static final String ROOT_URL = "http://translate.google.com/toolkit/feeds";

	@Key("category")
	public String category;
	
	@Key("start-index")
	public String startIndex;
	
	@Key("max-results")
	public String maxResults;
	
	@Key("sharedwith")
	public String sharedwith;

	@Key("showdeleted")
	public Boolean showdeleted;

	@Key("onlydeleted")
	public Boolean onlydeleted;

	@Key("delete")
	public Boolean delete;
	
	@Key("scope")
	public String scope;

	public GttUrl(String url) {
		super(url);
	}

	public static GttUrl forRoot() {
		GttUrl rootUrl = new GttUrl(ROOT_URL);
	//	rootUrl.prettyprint = true;
		return rootUrl;
	}

	public static GttUrl forDocs() {
		GttUrl root = forRoot();
		root.getPathParts().add("documents");
		return root;
	}
	
	public static GttUrl forDoc(String id) {
		assertNotNull(id);
		GttUrl document = forDocs();
		document.getPathParts().add(id);
		return document;
	}
	
	public static GttUrl forDocsSharedWith(String email) {
		assertNotNull(email);
		GttUrl root = forRoot();
		root.getPathParts().add("documents");
		root.sharedwith = email;
		return root;
	}
	
	public static GttUrl forTrashedDocs() {
		GttUrl root = forRoot();
		root.getPathParts().add("documents");
		root.onlydeleted = true;
		return root;
	}
	
	public static GttUrl forCategory(String category) {
		assertNotNull(category);
		GttUrl root = forRoot();
		root.getPathParts().add("documents");
		root.category = category;
		return root;
	}

	public static GttUrl forTranslMemories() {
		GttUrl root = forRoot();
		root.getPathParts().add("tm");
		return root;
	}

	public static GttUrl forTranslMemoriesByScope(String scope) {
		assertNotNull(scope);
		GttUrl root = forRoot();
		root.getPathParts().add("tm");
		root.scope = scope;
		return root;
	}

	public static GttUrl forGlossaries() {
		GttUrl root = forRoot();
		root.getPathParts().add("glossary");
		return root;
	}
	
	public static GttUrl forTranslationMemory(String id) {
		assertNotNull(id);
		GttUrl memory = forTranslMemories();
		memory.getPathParts().add(id);
		return memory;
	}
	
	public static GttUrl forGlossary(String id) {
		assertNotNull(id);
		GttUrl glossary = forGlossaries();
		glossary.getPathParts().add(id);
		return glossary;
	}
	
	public static GttUrl forAclOfDoc(String id) {
		assertNotNull(id);
		GttUrl documentAcl = forRoot();
		documentAcl.appendRawPath("/acl/documents");
		documentAcl.getPathParts().add(id);
		return documentAcl;
	}	

	public static GttUrl forAclOfDocumentWithScope(String id, String email) {
		assertNotNull(id);
		assertNotNull(email);
		GttUrl UserDocumentAcl = forAclOfDoc(id);
		UserDocumentAcl.getPathParts().add(email);
		return UserDocumentAcl;
	}	
	
	public static GttUrl forAclOfTranslationMemory(String id) {
		assertNotNull(id);
		GttUrl memoryAcl = forRoot();
		memoryAcl.appendRawPath("/acl/tm");
		memoryAcl.getPathParts().add(id);
		return memoryAcl;
	}	

	public static GttUrl forAclOfTranslationMemoryWithScope(String id, String email) {
		assertNotNull(id);
		assertNotNull(email);
		GttUrl UserTranslationMemoryAcl = forAclOfTranslationMemory(id);
		UserTranslationMemoryAcl.getPathParts().add(email);
		return UserTranslationMemoryAcl;
	}	
	
	public static GttUrl forAclOfGlossary(String id) {
		assertNotNull(id);
		GttUrl glossaryAcl = forRoot();
		glossaryAcl.appendRawPath("/acl/glossary");
		glossaryAcl.getPathParts().add(id);
		return glossaryAcl;
	}	

	public static GttUrl forAclOfGlossaryWithScope(String id, String email) {
		assertNotNull(id);
		assertNotNull(email);
		GttUrl UserGlossaryAcl = forAclOfGlossary(id);
		UserGlossaryAcl.getPathParts().add(email);
		return UserGlossaryAcl;
	}	
	
	private static void assertNotNull(String param) {
		if(param == null) {
			throw new IllegalArgumentException("Url parameter must not be null");
		}
	}
	
	@Override
	public String toString()  {
		String xml = RequestCallback.GTT_DICTIONARY.toStringOf("gttUrl", this);
		return PrintUtils.prettyFormat(xml);
	}
}
