package de.ude.openlap.xapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.ude.openlap.xapi.repo.StatementsRepo;

@RestController
@RequestMapping("/v1/statements/")
public class StatementsController {
	@Autowired
	private StatementsRepo statementsRepo;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String getAllStatements() throws IOException {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String activiteslist = gson.toJson(statementsRepo.findAll(new PageRequest(1, 20)));
		return activiteslist;

	}

	/*
	 * @RequestMapping("/learninglocker/statements/")
	 * 
	 * @ResponseBody public String getStatements() throws IOException {
	 * 
	 * String statements = null; statements = xAPI.getStatementsByQuery("hello");
	 * return statements;
	 * 
	 * }
	 */
}
