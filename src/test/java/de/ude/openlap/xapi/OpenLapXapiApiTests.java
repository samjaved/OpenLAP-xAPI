package de.ude.openlap.xapi;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import de.ude.openlap.xapi.dto.LoginUser;
import de.ude.openlap.xapi.dto.QueryParameters;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class OpenLapXapiApiTests implements UserAuthenticationTests, XapiStatementTests {
	/*
	 * WebApplicationContext (wac) provides the web application configuration. It
	 * loads all the application beans and controllers into the context.
	 */
	@Autowired
	private WebApplicationContext wac;
	/*
	 * MockMvc provides support for Spring MVC testing. It encapsulates all web
	 * application beans and make them available for testing.
	 */
	private MockMvc mockMvc;

	/**
	 * For converting exceptions thrown by the spring security to status codes e.g
	 * bad credential exception to 401 status code
	 */
	@Resource
	private FilterChainProxy springSecurityFilterChain;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
	}


	@Override
	@Test
	public void nonexistentUserCannotGetToken() throws Exception {
		String username = "sammar@ude.de";
		String password = "password234";
		LoginUser login = new LoginUser();
		login.setEmail(username);
		login.setPassword(password);
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/v1/token/generate-token").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(IntegrationTestUtil.convertObjectToJsonBytes(login)))
				.andExpect(status().isUnauthorized()).andReturn();

	}
	@Override
	@Test
	public void existentUserCanGetTokenAndAuthentication() throws Exception {
		String username = "sammar@ude.de";
		String password = "password123";
		LoginUser login = new LoginUser();
		login.setEmail(username);
		login.setPassword(password);
		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/v1/token/generate-token").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(IntegrationTestUtil.convertObjectToJsonBytes(login)))
				.andExpect(status().isOk()).andReturn();

		/*
		 * this.mockMvc.perform(MockMvcRequestBuilders.get("/test")
		 * .header("Authorization", "Bearer " + token)) .andExpect(status().isOk());
		 */
	}

	@Override
	@Test
	public void getAllVerbsWithoutUserAuthentication() throws Exception {
		// TODO Auto-generated method stub
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/v1/statements/verbs/list"))
				.andExpect(status().isUnauthorized());
	}

	@Override
	@Test
	public void getAllVerbsAfterUserAuthentication() throws Exception {

		String username = "sammar@ude.de";
		String password = "password123";
		LoginUser login = new LoginUser();
		login.setEmail(username);
		login.setPassword(password);
		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/v1/token/generate-token").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(IntegrationTestUtil.convertObjectToJsonBytes(login)))
				.andExpect(status().isOk()).andReturn();

		String response = result.getResponse().getContentAsString();
		JSONObject tokenObject = new JSONObject(response);
		String token = tokenObject.get("token").toString();

		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/v1/statements/verbs/list").header("Authorization", "Bearer " + token))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].statement.verb.id",
						is("http://www.tincanapi.co.uk/verbs/enrolled_onto_learning_plan")))
				.andExpect(jsonPath("$[0].statement.verb.display.en", is("enrolled onto")));
	}

	@Override
	@Test
	public void getStatementsByCustomQueryWithoutUserAuthentication() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.post("/v1/statements/list/ByCustomQuery"))
				.andExpect(status().isUnauthorized());
	}

	@Override
	@Test
	public void getStatementsByCustomQueryWithoutAnyParameterAfterAuthentication() throws Exception {
		String username = "sammar@ude.de";
		String password = "password123";
		LoginUser login = new LoginUser();
		login.setEmail(username);
		login.setPassword(password);
		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/v1/token/generate-token").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(IntegrationTestUtil.convertObjectToJsonBytes(login)))
				.andExpect(status().isOk()).andReturn();

		String response = result.getResponse().getContentAsString();
		JSONObject tokenObject = new JSONObject(response);
		String token = tokenObject.get("token").toString();

		QueryParameters query = new QueryParameters();



		Object[] mandatory = new Object[] { new Object() };
		Object[] optional = new Object[] { new Object() };
		Object returnParameters = new Object();

		query.setQuery(mandatory);
		query.setStatementDuration(optional);
		query.setParametersToBeReturnedInResult(returnParameters);

		Gson gson = new Gson();

		String queryComplusoryParameters = gson.toJson(query.getParametersToBeReturnedInResult());

		System.out.println(queryComplusoryParameters);



		this.mockMvc
				.perform(MockMvcRequestBuilders
						.post("/v1/statements/list/ByCustomQuery").header("Authorization", "Bearer " + token)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.content(IntegrationTestUtil.convertObjectToJsonBytes(query)))
				.andExpect(status().isOk())
				.andReturn();

	}

}
