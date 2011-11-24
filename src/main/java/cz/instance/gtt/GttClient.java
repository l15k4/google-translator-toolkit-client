package cz.instance.gtt;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.http.HttpMethod;

import cz.instance.gtt.model.AclEntry;
import cz.instance.gtt.model.GlossaryEntry;
import cz.instance.gtt.model.GttEntry;
import cz.instance.gtt.model.GttFeed;
import cz.instance.gtt.model.GttUrl;
import cz.instance.gtt.model.Link;
import cz.instance.gtt.model.TmEntry;

public class GttClient {
	
	private GenericRequestExecutor exe;
	private EntryBuilder eBuilder;
	
	/** 
	 * GET
	 */

	public GttClient(GenericRequestExecutor executor, EntryBuilder entryBuilder) {
		this.exe = executor;
		this.eBuilder = entryBuilder;
	}

	/** Documents */

	public GttFeed<GttEntry> getAllDocs() throws IOException {
		return exe.toFeed(GttUrl.forDocs(), HttpMethod.GET, GttEntry.class);
	}

	public GttFeed<GttEntry> getTrashedDocs() throws IOException {
		return exe.toFeed(GttUrl.forTrashedDocs(), HttpMethod.GET, GttEntry.class);
	}

	public GttFeed<GttEntry> getHiddenDocs() throws IOException {
		return exe.toFeed(GttUrl.forCategory("hidden"), HttpMethod.GET, GttEntry.class);
	}

	public GttFeed<GttEntry> getDocsSharedWith(String scopeValue) throws IOException {
		return exe.toFeed(GttUrl.forDocsSharedWith(scopeValue), HttpMethod.GET, GttEntry.class);
	}

	public GttEntry getDoc(String urlId) throws IOException {
		return exe.toEntry(GttUrl.forDoc(urlId), HttpMethod.GET, GttEntry.class);
	}

	/** Translation Memories */

	public GttFeed<TmEntry> getTranslMemories() throws IOException {
		return exe.toFeed(GttUrl.forTranslMemories(), HttpMethod.GET, TmEntry.class);
	}

	public GttFeed<TmEntry> getTranslMemoriesByScope(String scope) throws IOException {
		return exe.toFeed(GttUrl.forTranslMemoriesByScope(scope), HttpMethod.GET, TmEntry.class);
	}

	public TmEntry getTranslMemory(String idUrl) throws IOException {
		return exe.toEntry(new GttUrl(idUrl), HttpMethod.GET, TmEntry.class);
	}

	/** Glossaries */

	public GttFeed<GlossaryEntry> getGlossaries() throws IOException {
		return exe.toFeed(GttUrl.forGlossaries(), HttpMethod.GET, GlossaryEntry.class);
	}

	public GlossaryEntry getGlossary(String idUrl) throws IOException {
		return exe.toEntry(new GttUrl(idUrl), HttpMethod.GET, GlossaryEntry.class);
	}

	/** Access Control List */

	public GttFeed<AclEntry> getAclForDoc(String docId) throws IOException {
		return exe.toFeed(GttUrl.forAclOfDoc(docId), HttpMethod.GET, AclEntry.class);
	}

	public AclEntry getAclForDocByScope(String docId, String scopeValue) throws IOException {
		return exe.toEntry(GttUrl.forAclOfDocumentWithScope(docId, scopeValue), HttpMethod.GET, AclEntry.class);
	}
	
	public List<AclEntry> getAclsForDocByRole(String docId, String roleValue) throws IOException {
		GttFeed<AclEntry> aclFeed = getAclForDoc(docId);
		List<AclEntry> aclEntries = new ArrayList<AclEntry>();
		for (AclEntry aclEntry : aclFeed.getFeedEntries()) {
			if(aclEntry.role != null && aclEntry.role.value.equals(roleValue)) {
				aclEntries.add(aclEntry);
			}
		}
		return aclEntries;
	}

	public InputStream getFile(String urlId) throws IOException {
		GttEntry entry = getDoc(urlId);
		String editUrl = null;
		for (Link link : entry.links) {
			if (link.rel.equals("edit")) {
				editUrl = link.href;
				break;
			}
		}
		if (editUrl == null)
			throw new RuntimeException("GttEntry must have edit link");

		return exe.toStream(new GttUrl(editUrl), HttpMethod.GET);
	}

	/** 
	 * 
	 * POST 
	 */

	/** Documents */

	public GttEntry postDoc(String mediaType, String title, String sourceLang, String targetLang, InputStream is) throws IOException {
		GttEntry entry = eBuilder.buildGttEntry(mediaType, title, sourceLang, targetLang);

		return exe.toEntry(GttUrl.forDocs(), HttpMethod.POST, GttEntry.class, entry, is);
	}

	public GttEntry postDocWithTM(String tmIdUrl, String mediaType, String title, String sourceLang, String targetLang, InputStream is) throws IOException {
		GttEntry entry = eBuilder.buildGttEntryWithTM(tmIdUrl, mediaType, title, sourceLang, targetLang);

		return exe.toEntry(GttUrl.forDocs(), HttpMethod.POST, GttEntry.class, entry, is);
	}

	public GttEntry postDocWithGlossary(String gIdUrl, String mediaType, String title, String sourceLang, String targetLang, InputStream is) throws IOException {
		GttEntry entry = eBuilder.buildGttEntryWithGlossary(gIdUrl, mediaType, title, sourceLang, targetLang);

		return exe.toEntry(GttUrl.forDocs(), HttpMethod.POST, GttEntry.class, entry, is);
	}

	public GttEntry postWebPage(String webPageUrl, String title, String sourceLang, String targetLang) throws IOException {
		GttEntry entry = eBuilder.buildWebPageEntry(webPageUrl, title, sourceLang, targetLang);

		return exe.toEntry(GttUrl.forDocs(), HttpMethod.POST, GttEntry.class, entry);
	}

	/** Access Control List */

	public AclEntry postAclForDoc(String docId, String scopeType, String scopeValue, String roleValue) throws IOException {
		AclEntry entry = eBuilder.buildAclEntry(scopeType, scopeValue, roleValue);

		return exe.toEntry(GttUrl.forAclOfDoc(docId), HttpMethod.POST, AclEntry.class, entry);
	}

	public List<AclEntry> postAclForDocs(List<String> docIds, String scopeType, String scopeValue, String roleValue) throws IOException {
		List<AclEntry> entries = new ArrayList<AclEntry>();
		for (String docId : docIds) {
			entries.add(postAclForDoc(docId, scopeType, scopeValue, roleValue));
		}
		return entries;
	}

	/**
	 * PUT 
	 */

	/** Documents */

	// updating only title, for reference, other metadata might be updated (gloss, tm)
	public GttEntry updateDoc(GttEntry targetEntry, String title) throws IOException {
		GttEntry entry = eBuilder.updateEntry(targetEntry, title);

		return exe.toEntry(GttUrl.forDocs(), HttpMethod.PUT, GttEntry.class, entry);
	}

	/** Access Control List */

	public AclEntry updateAcl(String docId, String scopeType, String scopeValue, String roleValueToUpdate) throws IOException {
		AclEntry entry = eBuilder.buildAclEntry(scopeType, scopeValue, roleValueToUpdate);

		return exe.toEntry(GttUrl.forAclOfDocumentWithScope(docId, scopeValue), HttpMethod.PUT, AclEntry.class, entry);
	}

	/** 
	 * DELETE 
	 */

	/** Documents */

	public boolean deleteDoc(String url, boolean hardDelete) throws IOException {
		GttUrl gu = new GttUrl(url);
		if (hardDelete)
			gu.delete = true;
		return exe.toBoolean(gu, HttpMethod.DELETE);
	}

	public boolean deleteAllDocs(boolean hardDelete) throws IOException {
		GttFeed<GttEntry> oldFeed = getAllDocs();
		for (GttEntry e : oldFeed.getFeedEntries()) {
			deleteDoc(e.id, hardDelete);
		}
		return true;
	}

	/** Translation Memories */

	public boolean deleteTranslMemory(String url) throws IOException {
		return exe.toBoolean(new GttUrl(url), HttpMethod.DELETE);
	}

	/** Glossaries */

	public boolean deleteGlossary(String url) throws IOException {
		return exe.toBoolean(new GttUrl(url), HttpMethod.DELETE);
	}

	/** Access Control List */

	public boolean removeAcl(String docId, String scopeValue) throws IOException {
		return exe.toBoolean(GttUrl.forAclOfDocumentWithScope(docId, scopeValue), HttpMethod.DELETE);
	}	
}
