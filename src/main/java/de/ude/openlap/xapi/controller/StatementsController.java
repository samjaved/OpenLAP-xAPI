package de.ude.openlap.xapi.controller;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.rwthaachen.openlap.dataset.OpenLAPDataSet;
import de.rwthaachen.openlap.exceptions.OpenLAPDataColumnException;
import de.ude.openlap.xapi.dto.QueryParameters;
import de.ude.openlap.xapi.service.StatementService;


@RestController
@RequestMapping("/v1/statements/")
public class StatementsController {

	@Autowired
	private StatementService statementService;

	/**
	 * This function can only be executed if http request contains Bearer token(JWT
	 * token received through login request) and logged user role should be
	 * site_admin
	 * 
	 * @param OrganizationId
	 * @param LrsId
	 * @return OpenLAPDataSet of all the verbs saved through xAPI statements
	 * @throws IOException
	 * @throws JSONException
	 * @throws OpenLAPDataColumnException
	 */
	@PreAuthorize("hasRole('site_admin')")
	@RequestMapping(value = "/verbs/list", method = RequestMethod.GET)
	@ResponseBody
	public OpenLAPDataSet verbsList(@RequestParam("OrganizationId") ObjectId organizationId,
			@RequestParam("LrsId") ObjectId lrsId) throws IOException, JSONException, OpenLAPDataColumnException {

		return statementService.getAllVerbsFromStatements(organizationId, lrsId);
	}

	/**
	 * This function can only be executed if http request contains Bearer token(JWT
	 * token received through login request) and logged user role should be
	 * site_admin
	 * 
	 * @param queryParameters
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 * @throws OpenLAPDataColumnException
	 */
	@PreAuthorize("hasRole('site_admin')")
	//// @PreAuthorize("hasAnyRole('USER', 'site_admin')")
	@RequestMapping(value = "/list/ByCustomQuery", method = RequestMethod.POST)
	@ResponseBody
	public OpenLAPDataSet statementsByCustomQuery(@RequestParam("OrganizationId") ObjectId organizationId,
			@RequestParam("LrsId") ObjectId lrsId,
			@RequestBody QueryParameters queryParameters)
			throws IOException, JSONException, OpenLAPDataColumnException {
		long startTime = System.currentTimeMillis();
		OpenLAPDataSet Test = statementService.getAllStatementsByCustomQuery(organizationId, lrsId, queryParameters);

		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime) + " milliseconds");
		return Test;

	}

}
