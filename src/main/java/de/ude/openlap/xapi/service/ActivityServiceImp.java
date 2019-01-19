package de.ude.openlap.xapi.service;

import java.util.ArrayList;
import java.util.Iterator;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import de.rwthaachen.openlap.dataset.OpenLAPColumnDataType;
import de.rwthaachen.openlap.dataset.OpenLAPDataSet;
import de.rwthaachen.openlap.exceptions.OpenLAPDataColumnException;
import de.ude.openlap.xapi.dto.OpenLapDataConverter;
import de.ude.openlap.xapi.model.Activitiy;
import de.ude.openlap.xapi.repo.ActivitiyRepo;

@Service
public class ActivityServiceImp implements ActivityService {
	@Autowired
	private ActivitiyRepo activityRepo;

	@Override
	public OpenLAPDataSet getActivities(ObjectId OrganizationId, ObjectId lrsId)
			throws OpenLAPDataColumnException, JSONException {

		ArrayList<Object> listOfActivityTypes = new ArrayList<Object>();
		ArrayList<Object> listOfActivityNames = new ArrayList<Object>();
		ArrayList<String> listOfActivityIds = new ArrayList<String>();
		ArrayList<Object> listOfActivityDescription = new ArrayList<Object>();
		ArrayList<String> listOfActivityExtensionIds = new ArrayList<String>();
		ArrayList<String> listOfActivityExtensionContextKeys = new ArrayList<String>();
		for (Activitiy activity : activityRepo.findActivitiesByOrganizationAndLrs(OrganizationId, lrsId)) {
			if (activity.getType() != null) {
				listOfActivityTypes.add(activity.getType());
			}
			if (activity.getActivityId() != null) {
				listOfActivityIds.add(activity.getActivityId());
			}

			if (activity.getName() != null) {
				String activityName = new Gson().toJson(activity.getName());
				JSONObject activityNameObject = new JSONObject(activityName);
				Iterator<?> displaykey = activityNameObject.keys();

				while (displaykey.hasNext()) {
					// loop to get the dynamic key
					String DynamicLanguageKey = (String) displaykey.next();

					// get the value of the dynamic key
					if (!listOfActivityNames.contains(activityNameObject.get(DynamicLanguageKey))) {
						listOfActivityNames.add(activityNameObject.get(DynamicLanguageKey));
					}

				}
			}
			if (activity.getDescription() != null) {
				String activityDescription = new Gson().toJson(activity.getDescription());

				JSONObject activityDescriptionObject = new JSONObject(activityDescription);
				Iterator<?> descriptionKey = activityDescriptionObject.keys();

				while (descriptionKey.hasNext()) {
					// loop to get the dynamic key
					String DynamicdescriptionKey = (String) descriptionKey.next();

					// get the value of the dynamic key
					if (!listOfActivityDescription.contains(activityDescriptionObject.get(DynamicdescriptionKey))) {
						listOfActivityDescription.add(activityDescriptionObject.get(DynamicdescriptionKey));
					}

				}
			}
			if (activity.getExtensions() != null) {
				String activityExtension = new Gson().toJson(activity.getExtensions());

				JSONObject activityExtensionObject = new JSONObject(activityExtension);
				Iterator<?> extentionKey = activityExtensionObject.keys();
				while (extentionKey.hasNext()) {
					// loop to get the dynamic key
					String DynamicextentionKey = (String) extentionKey.next();

					// get the value of the dynamic key
					if (!listOfActivityExtensionIds.contains(DynamicextentionKey)) {
						listOfActivityExtensionIds.add(DynamicextentionKey);
					}
					if (DynamicextentionKey != null) {
						JSONObject contextualObject = activityExtensionObject.getJSONObject(DynamicextentionKey);
						Iterator<?> contextKey = contextualObject.keys();
						while (contextKey.hasNext()) {
							// loop to get the dynamic key
							String DynamiccontextKey = (String) contextKey.next();

							// get the value of the dynamic key
							if (!listOfActivityExtensionContextKeys.contains(DynamiccontextKey)) {
								listOfActivityExtensionContextKeys.add(DynamiccontextKey);
							}

						}
					}

				}
			}

		}
		OpenLapDataConverter dataConveter = new OpenLapDataConverter();
		dataConveter.SetOpenLapDataColumn("ActivityTypes", OpenLAPColumnDataType.Text, true, listOfActivityTypes);
		dataConveter.SetOpenLapDataColumn("ActivityNames", OpenLAPColumnDataType.Text, true, listOfActivityNames);
		dataConveter.SetOpenLapDataColumn("ActivityIds", OpenLAPColumnDataType.Text, true, listOfActivityIds);
		dataConveter.SetOpenLapDataColumn("ActivityDescription", OpenLAPColumnDataType.Text, true,
				listOfActivityDescription);

		dataConveter.SetOpenLapDataColumn("ActivityExtentionIds", OpenLAPColumnDataType.Text, true,
				listOfActivityExtensionIds);
		dataConveter.SetOpenLapDataColumn("ActivityExtentionContextKeys", OpenLAPColumnDataType.Text, true,
				listOfActivityExtensionContextKeys);
		return dataConveter.getDataSet();
	}

	@Override
	public OpenLAPDataSet getActivitiesExtensionContextValues(ObjectId OrganizationId, ObjectId lrsId,
			String extensionId, String extensionContextKey) throws OpenLAPDataColumnException, JSONException {
		ArrayList<Object> listOfExtensionContextValues = new ArrayList<Object>();
		for (Activitiy activity : activityRepo.findContextualFieldValuesByExtensionUrlAndKey(OrganizationId, lrsId,
				"extensions", extensionId, extensionContextKey)) {
			if (activity.getExtensions() != null) {
				String activityExtension = new Gson().toJson(activity.getExtensions());
				JSONObject activityExtensionObject = new JSONObject(activityExtension);
				if (!activityExtensionObject.toString().equals("{}")) {
					JSONObject extensionIdObject = activityExtensionObject.getJSONObject(extensionId);
					if (!listOfExtensionContextValues.contains(extensionIdObject.get(extensionContextKey))) {
						listOfExtensionContextValues.add(extensionIdObject.get(extensionContextKey));
					}
				}
			}
		}

		OpenLapDataConverter dataConveter = new OpenLapDataConverter();
		dataConveter.SetOpenLapDataColumn(extensionContextKey, OpenLAPColumnDataType.Text, true,
				listOfExtensionContextValues);

		return dataConveter.getDataSet();
	}
}
