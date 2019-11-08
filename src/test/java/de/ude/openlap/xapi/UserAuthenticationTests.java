package de.ude.openlap.xapi;

public interface UserAuthenticationTests {

	public void nonexistentUserCannotGetToken() throws Exception;

	public void existentUserCanGetTokenAndAuthentication() throws Exception;

}
