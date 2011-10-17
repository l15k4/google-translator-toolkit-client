package cz.instance.gtt.model;

import com.google.api.client.util.Key;

public class GttEntry extends Entry {
	
	public GttEntry() {
	}

	public GttEntry(String title, Content content, String sourceLang, String targetLang) {
		super(title);
		this.content = content;
		this.sourceLanguage = sourceLang;
		this.targetLanguage = targetLang;
	}
	
	public GttEntry(String title, DocumentSource docSource, String sourceLang, String targetLang) {
		super(title);
		this.documentSource = docSource;
		this.sourceLanguage = sourceLang;
		this.targetLanguage = targetLang;
	}

	@Key("@gd:etag")
	public String etag;
	
	@Key("published")
	public String published;
	
	@Key("gd:lastModifiedBy")
	public LastModifiedBy lastModifiedBy;
	
	@Key("content")
	public Content content;
	
	@Key("gtt:documentSource")
	public DocumentSource documentSource;	
		
	@Key("gtt:sourceLanguage")
	public String sourceLanguage;
	
	@Key("gtt:targetLanguage")
	public String targetLanguage;
	
	@Key("gtt:numberOfSourceWords")
	public Integer numberOfSourceWords;
	
	@Key("gtt:percentComplete")
	public Integer percentComplete;
	
	@Key("category")
	public Category category; 
	
	@Key("gtt:translationMemory")
	public TranslationMemory translationMemory;

	@Key("gtt:glossary")
	public Glossary glossary;
		
}
