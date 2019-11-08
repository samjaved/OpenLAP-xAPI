package de.ude.openlap.xapi;

public interface XapiStatementTests {
	void getAllVerbsWithoutUserAuthentication() throws Exception;
	void getAllVerbsAfterUserAuthentication() throws Exception;
	void getStatementsByCustomQueryWithoutUserAuthentication() throws Exception;
	void getStatementsByCustomQueryWithoutAnyParameterAfterAuthentication() throws Exception;
}
