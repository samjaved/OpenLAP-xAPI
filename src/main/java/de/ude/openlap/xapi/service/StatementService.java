package de.ude.openlap.xapi.service;

import org.bson.types.ObjectId;
import org.json.JSONException;

import de.rwthaachen.openlap.dataset.OpenLAPDataSet;
import de.rwthaachen.openlap.exceptions.OpenLAPDataColumnException;
import de.ude.openlap.xapi.dto.QueryParameters;

public interface StatementService {
	OpenLAPDataSet getAllVerbsFromStatements(ObjectId organizationId, ObjectId lrsId)
			throws JSONException, OpenLAPDataColumnException;

	OpenLAPDataSet getAllStatementsByCustomQuery(ObjectId organizationId, ObjectId lrsId,
			QueryParameters queryParameters) throws JSONException, OpenLAPDataColumnException;

}
