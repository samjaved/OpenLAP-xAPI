package de.ude.openlap.xapi.service;

import java.io.IOException;

import org.json.JSONArray;

import com.fasterxml.jackson.databind.MappingIterator;

import de.ude.openlap.xapi.dto.Statement;

public interface CsvToJsonService {

	public MappingIterator<Statement> readStatementsFromCsv(String InptFile);

	public JSONArray convertCsvStatementsToXapiStatements(MappingIterator<Statement> csvStatements) throws IOException;
}
