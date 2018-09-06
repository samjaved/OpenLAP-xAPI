package de.ude.openlap.xapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.ude.openlap.xapi.repo.StatementsRepo;
import de.ude.openlap.xapi.service.XapiStatements;

@Controller
public class StatementsController {
	@Autowired
	private StatementsRepo statementsRepo;
	@Autowired
	private XapiStatements xAPI;

	@RequestMapping("/learninglocker/statements/")
	@ResponseBody
	public String getStatements() throws IOException {

		String statements = null;
		statements = xAPI.getStatementsByQuery("hello");
		return statements;

	}
}
