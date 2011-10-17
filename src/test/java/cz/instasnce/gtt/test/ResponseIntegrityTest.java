package cz.instasnce.gtt.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.instance.gtt.model.AclEntry;
import cz.instance.gtt.model.GttEntry;
import cz.instance.gtt.model.GttFeed;

public class ResponseIntegrityTest extends GenericTesting {

	private List<GttEntry> feedEntries;
	private int totalResults;

	private List<GttFeed<AclEntry>> aclFeeds;
	private List<String> docIDs;

	@BeforeClass
	public void prepare() throws Exception {
		docIDs = getDocumentIDs(gttFeed);
		feedEntries = gttFeed.getFeedEntries();
		totalResults = gttFeed.totalResults;
		aclFeeds = getAclFeeds(docIDs);
	}

	@Test
	public void feedFieldsMustExist() throws Exception {
		List<Field> fields = getFieldsForType(GttFeed.class, false);
		for (int i = 0; i < fields.size(); i++) {
			assertNotNull(fields.get(i).get(gttFeed));
		}
	}

	/** apart from category, translationMemory and glossary all fields must be present 
	 * these fields are present only when document is Completed or have TM / glossary
	 */
	@Test
	public void gttEntryFieldsMustExist() throws Exception {
		List<String> ignoredNames = Arrays.asList("category", "translationMemory", "glossary");
		List<Field> entryFields = getFieldsForType(GttEntry.class, true);
		for (int i = 0; i < feedEntries.size(); i++) {
			for (int j = 0; j < entryFields.size(); j++) {
				String name = entryFields.get(j).getName();
				if (!ignoredNames.contains(name)) {
					assertNotNull(entryFields.get(j).get(feedEntries.get(i)), entryFields.get(j).getName());
				}
			}
		}
	}

	@Test
	public void aclEntryFieldsMustExist() throws Exception {

		for (GttFeed<AclEntry> feed : aclFeeds) {
			List<AclEntry> aclEntries = feed.getFeedEntries();
			List<Field> entryFields = getFieldsForType(AclEntry.class, true);
			for (int i = 0; i < aclEntries.size(); i++) {
				for (int j = 0; j < entryFields.size(); j++) {
					assertNotNull(entryFields.get(j).get(aclEntries.get(i)), entryFields.get(j).getName());
				}
			}
		}
	}

	@Test
	public void feedEntriesMustExist() {
		assertTrue(totalResults > 0, "totalResults is <" + totalResults + ">");
		assertFalse(feedEntries.isEmpty(), "GttFeed Entries list is empty");
	}

	@Test
	public void totalResultsMustMatchEntriesCount() {
		assertTrue(totalResults == feedEntries.size(), "Number of entries <" + feedEntries.size() + "> doesn't match with totalResults + <" + totalResults + ">");
	}

	@Test
	public void entryFieldsMustMatch() throws Exception {
		assertEquals(docENEntry.title, docEN.getFile().getName(), "Titles don't match");
		assertEquals(docENEntry.sourceLanguage, docEN.getState(), "Source langs don't match");
		assertEquals(docENEntry.targetLanguage, targetLang, "Target langs don't match");
		assertEquals(odtDEEntry.title, odtDE.getFile().getName(), "Titles don't match");
		assertEquals(odtDEEntry.sourceLanguage, odtDE.getState(), "Source langs don't match");
		assertEquals(odtDEEntry.targetLanguage, targetLang, "Target langs don't match");
	}

	@Test
	public void aclFeedOfDocumentHasTwoAclEntries() throws Exception {
		for (GttFeed<AclEntry> f : aclFeeds) {
			assertEquals(f.entries.size(), 2, "There should be 2 ACL entries for documents with Owner & Writer");
		}
	}

	@Test
	public void allDocumentsHaveAclEntryForScope() throws Exception {
		List<AclEntry> ownerAclEntries = getAclEntriesOfScope(docIDs, clientId);
		List<AclEntry> writerAclEntries = getAclEntriesOfScope(docIDs, WRITER);
		assertEquals(ownerAclEntries.size(), docIDs.size(), "There should be as much AclEntries as documents with Owner");
		assertEquals(writerAclEntries.size(), docIDs.size(), "There should be as much AclEntries as documents with existing Writer");
	}
}
