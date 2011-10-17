package cz.instasnce.gtt.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeSuite;

import com.google.api.client.xml.GenericXml;

import cz.instance.gtt.AuthRequestFactory;
import cz.instance.gtt.GttClient;
import cz.instance.gtt.model.AclEntry;
import cz.instance.gtt.model.Entry;
import cz.instance.gtt.model.GttEntry;
import cz.instance.gtt.model.GttFeed;
import cz.instance.gtt.utils.LoggingUtils;
import cz.instance.utils.docs.Document;
import cz.instance.utils.docs.DocumentProvider;

public class GenericTesting {

	public static final String writerRole = "writer";
	public static final String targetLang = "cs";
	public static Document docEN;
	public static Document odtDE;
	public static GttFeed<GttEntry> gttFeed;
	public static GttEntry docENEntry;
	public static GttEntry odtDEEntry;
	public GttClient gttClient;

	/** WRITER is a user that is to be assigned a writer role */
	public static final String WRITER = "another-user@gmail.com";
	public static final String clientId = "user@gmail.com";
	public static final String passwd = "********";
	public static final String appName = "test-translator-toolkit-client-1.0";
	
	@BeforeSuite
	public void setUp() throws Exception {
		docEN = DocumentProvider.getDocByTypeAndLang("txt", "es");
		odtDE = DocumentProvider.getDocByTypeAndLang("txt", "it");
		LoggingUtils.setLogging();
		
		AuthRequestFactory requestFactory = new AuthRequestFactory(clientId, passwd, appName);
		gttClient = requestFactory.getClient();

		/** post some docs */

		docENEntry = gttClient.postDoc(docEN.getMediaTypeString(), docEN.getFile().getName(), docEN.getState(), targetLang, docEN.getInputStream());
		odtDEEntry = gttClient.postDoc(odtDE.getMediaTypeString(), odtDE.getFile().getName(), odtDE.getState(), targetLang, odtDE.getInputStream());

		/** set up ACL */

		gttClient.postAclForDoc(docENEntry.getIdString(), "user", WRITER, writerRole);
		gttClient.postAclForDoc(odtDEEntry.getIdString(), "user", WRITER, writerRole);

		gttFeed = gttClient.getAllDocs();
	}

	public List<Field> getFieldsForType(Class<?> type, boolean forParent) {
		List<Field> fields = new ArrayList<Field>();

		fields.addAll(Arrays.asList(type.getDeclaredFields()));

		if (forParent) {
			for (Class<?> c = type; c != GenericXml.class && c != Object.class; c = c.getSuperclass()) {
				fields.addAll(Arrays.asList(c.getDeclaredFields()));
			}
		}
		return fields;
	}

	public List<GttFeed<AclEntry>> getAclFeeds(List<String> docIDs) throws Exception {
		List<GttFeed<AclEntry>> feeds = new ArrayList<GttFeed<AclEntry>>();
		for (String docId : docIDs) {
			feeds.add(gttClient.getAclForDoc(docId));
		}
		return feeds;
	}

	public List<AclEntry> getAclEntriesOfScope(List<String> docIDs, String userEmail) throws Exception {
		List<AclEntry> aclScopeEntries = new ArrayList<AclEntry>();
		for (String docId : docIDs) {
			aclScopeEntries.add(gttClient.getAclForDocByScope(docId, userEmail));
		}
		return aclScopeEntries;
	}
	
	public <T extends Entry> List<String> getDocumentIDs(GttFeed<T> feed) {
		List<T> entries = feed.getFeedEntries();
		List<String> ids = new ArrayList<String>();
		for (Entry e : entries) {
			ids.add(e.getIdString());
		}
		return ids;
	}
}
