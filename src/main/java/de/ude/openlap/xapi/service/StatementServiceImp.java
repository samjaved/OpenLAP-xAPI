package de.ude.openlap.xapi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.map.MultiValueMap;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import de.rwthaachen.openlap.dataset.OpenLAPColumnDataType;
import de.rwthaachen.openlap.dataset.OpenLAPDataSet;
import de.rwthaachen.openlap.exceptions.OpenLAPDataColumnException;
import de.ude.openlap.xapi.dto.OpenLapDataConverter;
import de.ude.openlap.xapi.dto.QueryParameters;
import de.ude.openlap.xapi.dto.Verb;
import de.ude.openlap.xapi.model.Statement;
import de.ude.openlap.xapi.repo.StatementRepo;

@Service
public class StatementServiceImp implements StatementService {
	@Autowired
	private StatementRepo statementsRepo;

	@Override
	public OpenLAPDataSet getAllVerbsFromStatements(ObjectId organizationId, ObjectId lrsId)
			throws JSONException, OpenLAPDataColumnException {

		ArrayList listOfVerbs = new ArrayList();
		ArrayList listOfVerbsIds = new ArrayList();
		for (Verb verb : statementsRepo.findAllVerbsByOrganizationAndLrs(organizationId, lrsId)) {

			String statement = new Gson().toJson(verb.getStatement());
			JSONObject statementObject = new JSONObject(statement);
			JSONObject verbObject = statementObject.getJSONObject("verb");
			if (!listOfVerbsIds.contains(verbObject.get("id"))) {
				listOfVerbsIds.add(verbObject.get("id"));
			}

			JSONObject displayObject = verbObject.getJSONObject("display");
			Iterator displaykey = displayObject.keys();

			while (displaykey.hasNext()) {
				// loop to get the dynamic key
				String DynamicLanguageKey = (String) displaykey.next();

				// get the value of the dynamic key
				if (!listOfVerbs.contains(displayObject.get(DynamicLanguageKey))) {
					listOfVerbs.add(displayObject.get(DynamicLanguageKey));
				}

			}

		}
		OpenLapDataConverter dataConveter = new OpenLapDataConverter();
		dataConveter.SetOpenLapDataColumn("VerbDisplayNames", OpenLAPColumnDataType.Text, true, listOfVerbs);
		dataConveter.SetOpenLapDataColumn("VerbIds", OpenLAPColumnDataType.Text, true, listOfVerbsIds);
		return dataConveter.getDataSet();
	}

	@Override
	public OpenLAPDataSet getAllStatementsByCustomQuery(ObjectId organizationId, ObjectId lrsId,
			QueryParameters queryParameters) throws JSONException, OpenLAPDataColumnException {

		ArrayList listOfKeys = new ArrayList();
		ArrayList listOfKeys2 = new ArrayList();
		Map<String, Object> items = new HashMap<>();
		MultiValueMap map = new MultiValueMap();
		String[] parts = null;
		String[] Testkey = null;

		Gson gson = new Gson();

		String query = gson.toJson(queryParameters.getQuery());
		String statementDuration = gson.toJson(queryParameters.getStatementDuration());
		String parametersToReceive = gson.toJson(queryParameters.getParametersToBeReturnedInResult());

		System.out.println(statementDuration);
		System.out.println(query);
		System.out.println(parametersToReceive);

		/*
		 * JSONArray jsonArray = new JSONArray(parametersToReceive);
		 * System.out.println(jsonArray);
		 */
		// "{'statement.context.instructor.name':'Tobias Kosche'}"
		@SuppressWarnings("deprecation")
		DBObject queryObject = (DBObject) JSON.parse(query);
		@SuppressWarnings("deprecation")
		DBObject statementDurationObject = (DBObject) JSON.parse(statementDuration);
		@SuppressWarnings("deprecation")
		DBObject parametersToReceiveObject = (DBObject) JSON.parse(parametersToReceive);
		System.out.println();
		Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
		String activiteslist = gson2.toJson(statementsRepo.findDataByCustomQuery(queryObject, statementDurationObject,
				parametersToReceiveObject, organizationId, lrsId));
		JSONObject statementObjecttest = new JSONObject(parametersToReceive);
		Iterator displaykey2 = statementObjecttest.keys();
		OpenLapDataConverter dataConveter = new OpenLapDataConverter();
		while (displaykey2.hasNext()) {
			// loop to get the dynamic key
			String returnKeys = (String) displaykey2.next();

			listOfKeys.add(returnKeys);
			// dataConveter.SetOpenLapDataColumn(returnKeys, OpenLAPColumnDataType.Text,
			// true, listOfKeys);

		}
		JSONArray jaarr = new JSONArray();
		ArrayList<ArrayList<String>> dice = new ArrayList<ArrayList<String>>();

		for (Statement statment : statementsRepo.findDataByCustomQuery(queryObject, statementDurationObject,
				parametersToReceiveObject, organizationId, lrsId)) {
			String statement = new Gson().toJson(statment.getStatement());
			// listOfKeys.add(statement);
			JSONObject jo = new JSONObject();
			JSONObject statementObject = new JSONObject(statement);
			JSONObject verbObject = null;
			for (int count = 0; count < listOfKeys.size(); count++) {
				String returnKeys = (String) listOfKeys.get(count);
				ArrayList<String> die = new ArrayList<String>();
				parts = returnKeys.split("\\.");
				int count2;
				for (count2 = 1; count2 < parts.length - 1; count2++) {
					if (count2 == 1) {
						verbObject = statementObject.getJSONObject(parts[count2]);
					} else {
						verbObject = verbObject.getJSONObject(parts[count2]);
					}

				}

				System.out.println("testttt" + verbObject.has(parts[parts.length - 1]));
				// System.out.println(statementObject);
				if (verbObject.has(parts[parts.length - 1])) {

					if (!map.containsValue(verbObject.get(parts[parts.length - 1])))
					map.put(parts[parts.length - 1], verbObject.get(parts[parts.length - 1]));
				}
			}

		}
		System.out.println("test" + map);
		List list;
		Set entrySet = map.entrySet();
		Iterator it = entrySet.iterator();
		OpenLapDataConverter dataConveter2 = new OpenLapDataConverter();
		System.out.println("  Object key  Object value");
		ArrayList test = new ArrayList();
		ArrayList test2;
		while (it.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) it.next();
			list = (List) map.get(mapEntry.getKey());
			for (int j = 0; j < list.size(); j++) {

				if (!test.contains(mapEntry.getKey().toString())) {
					test.add(mapEntry.getKey().toString());

					test2 = new ArrayList();
					if (!test2.contains(mapEntry.getValue())) {
					test2.add(mapEntry.getValue());
					}

					dataConveter2.SetOpenLapDataColumn(mapEntry.getKey().toString(), OpenLAPColumnDataType.Text, true,
							test2);
				}

				System.out.println("\t" + mapEntry.getKey() + "\t  " + list.get(j));
			}
		}
		return dataConveter2.getDataSet();
	}

}
