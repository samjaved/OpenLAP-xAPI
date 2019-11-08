package de.ude.openlap.xapi.controller;

import java.io.IOException;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.MappingIterator;

import de.ude.openlap.xapi.dto.Statement;
import de.ude.openlap.xapi.service.CsvToJsonService;
import de.ude.openlap.xapi.service.ImportStatementsToLLService;

@Controller
public class ImportCsvController {

	@Autowired
	private CsvToJsonService converter;
	@Autowired
	private ImportStatementsToLLService xapiStatements;

	@RequestMapping("/import/csvdata")
	@ResponseBody
	public String index() throws IOException {
		int response;
		String result;
		MappingIterator<Statement> csvStatements = converter.readStatementsFromCsv("learning_locker_dump.csv");
		JSONArray xapiStatementsFromCsv = converter.convertCsvStatementsToXapiStatements(csvStatements);
		response = xapiStatements.sendStatementsToLL(xapiStatementsFromCsv);
		result = "Status code" + response + "\nReturned from Learning Locker";
		if (response == 200) {

			result = result + xapiStatementsFromCsv.length() + "\nRecords Inserted successfully";

		} else {
			result = result + "\nRecords Can't be inserted into Learning Locker";
		}
		return result;

	}

}
