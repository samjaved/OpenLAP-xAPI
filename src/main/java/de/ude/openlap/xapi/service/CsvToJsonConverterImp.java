package de.ude.openlap.xapi.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import de.ude.openlap.xapi.dto.Statement;

@Service("csvToJsonConverter")
public class CsvToJsonConverterImp implements CsvToJsonConverter {

	@Override
	public MappingIterator<Statement> readStatementsFromCsv(String inputFile) {
		// TODO Auto-generated method stub
		InputStream csvStream;
		MappingIterator<Statement> csvStatements = null;
		try {
			csvStream = new FileInputStream(inputFile);
			CsvSchema bootstrap = CsvSchema.builder().build().withHeader();
			CsvMapper csvMapper = new CsvMapper();
			csvStatements = csvMapper.readerFor(Statement.class).with(bootstrap).readValues(csvStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return csvStatements;
	}

	@Override
	public JSONArray convertCsvStatementsToXapiStatements(MappingIterator<Statement> csvStatements) throws IOException {
		// TODO Auto-generated method stub
		JSONArray xApiStatements = new JSONArray();
		for (Statement truckEvent : csvStatements.readAll()) {
			JSONObject statement = new JSONObject();
			try {
				JSONObject verb = new JSONObject(truckEvent.getVerb());
				JSONObject actor = new JSONObject(truckEvent.getActor());
				JSONObject object = new JSONObject(truckEvent.getObject());

				statement.put("verb", verb);
				statement.put("actor", actor);
				statement.put("object", object);
				if (!truckEvent.getResult().isEmpty()) {
					JSONObject result = new JSONObject(truckEvent.getResult());
					statement.put("result", result);
				}
				if (!truckEvent.getContext().isEmpty()) {
					JSONObject context = new JSONObject(truckEvent.getContext());
					statement.put("context", context);
				}

				xApiStatements.put(statement);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return xApiStatements;
	}

}
