package de.ude.openlap.xapi.service;

import org.bson.types.ObjectId;
import org.json.JSONException;

import de.rwthaachen.openlap.dataset.OpenLAPDataSet;
import de.rwthaachen.openlap.exceptions.OpenLAPDataColumnException;

public interface LrsService {
	OpenLAPDataSet listOfLrsByOrganization(ObjectId organizationId) throws OpenLAPDataColumnException, JSONException;
}
