package de.ude.openlap.xapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import de.ude.openlap.xapi.dto.QueryParameters;
import de.ude.openlap.xapi.repo.StatementRepo;

@RestController
@RequestMapping("/v1/statements/")
public class StatementsController {
	@Autowired
	private StatementRepo statementsRepo;


	@PreAuthorize("hasRole('site_admin')")
	@RequestMapping(value = "/verbs/list", method = RequestMethod.GET)
	@ResponseBody
	public String getAllVerbsFromStatements() throws IOException {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String activiteslist = gson.toJson(statementsRepo.findAllVerbs());
		return activiteslist;

	}

	@PreAuthorize("hasRole('site_admin')")
	//// @PreAuthorize("hasAnyRole('USER', 'site_admin')")
	@RequestMapping(value = "/list/ByCustomQuery", method = RequestMethod.POST)
	@ResponseBody
	public String getAllStatementsByCustomQuery(@RequestBody QueryParameters queryParameters) throws IOException {

		Gson gson = new Gson();
		String queryOptionalParameters = gson.toJson(queryParameters.getQueryOptionalParameters());
		String queryComplusoryParameters = gson.toJson(queryParameters.getQueryMandatoryParameters());
		String parametersToReceive = gson.toJson(queryParameters.getParametersToBeReturnedInResult());
		System.out.println(queryComplusoryParameters);
		System.out.println(queryOptionalParameters);
		System.out.println(parametersToReceive);

		// "{'statement.context.instructor.name':'Tobias Kosche'}"
		DBObject queryOptionalObject = (DBObject) JSON.parse(queryOptionalParameters);
		DBObject queryComplusoryObject = (DBObject) JSON.parse(queryComplusoryParameters);
		DBObject parametersToReceiveObject = (DBObject) JSON.parse(parametersToReceive);

		Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
		StopWatch watch = new StopWatch();
		watch.start();
		String activiteslist = gson2
				.toJson(statementsRepo.findDataByCustomQuery(queryOptionalObject, queryComplusoryObject,
						parametersToReceiveObject));
		watch.stop();
		System.out.println("Total execution time to create 1000K objects in Java using StopWatch in millis: "
				+ watch.getTotalTimeMillis());
		return activiteslist;



	}

}
