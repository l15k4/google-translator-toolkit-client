Google Translator Toolkit API Java Client 

NOTE: this is an inital commit ! Refactoring will be expected. Please do not rely on it too much now.

Dependencies: 

com.google.api-client version 1.5-beta
https://github.com/l15k4/document-provider  &  testng 
     - only for running tests ! If you want to run them, you need to modify GenericTesting (clientId, passwd, WRITER)

	 Usage:

	 mvn install -DskipTests

	 AuthRequestFactory requestFactory = new AuthRequestFactory(clientId, passwd, appName);
	 GttClient gttClient = requestFactory.getClient();
	 gttClient.apiCalls( );

	 // Google Translator Toolkit API doesn't support OAuth ! Only client login is implemented.
         // So be careful when storing password to your account ! 

IMPLEMENTED :

	 Retrieving translation documents
	 Retrieving all translation documents
	 Retrieving trashed translation documents
	 Retrieving hidden translation documents
	 Retrieving translation documents shared with a user
	 Retrieving translation memories
	 Retrieving all translation memories
	 Retrieving translation memories filtered by scope
	 Retrieving glossaries
	 Retrieving all glossaries
	 Creating/Uploading translation documents
	 Uploading translation documents
	 Uploading translation documents with attached translation memory or glossary
	 Uploading web page
	 Updating translation documents
	 Updating translation document's metadata
	 Deleting translation documents
	 Deleting translation memories
	 Deleting glossaries
	 Downloading translation documents
	 Modifying sharing permissions
	 Overview of Sharing (ACLs)
         Retrieving the ACL feed
         Adding sharing permissions
         Updating sharing permissions
         Removing sharing permissions


NOT IMPLEMENTED :
						    
    - Uploading translation memory
    - Uploading glossary
    - Creating empty translation memory with metadata (no content)
    - Updating translation memory metadata
    - Updating translation memory content
    - Updating translation memory metadata and content
    - Updating glossary metadata
    - Updating glossary content
    - Updating glossary metadata and content

