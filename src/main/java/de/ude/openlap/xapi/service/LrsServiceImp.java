package de.ude.openlap.xapi.service;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.rwthaachen.openlap.dataset.OpenLAPColumnDataType;
import de.rwthaachen.openlap.dataset.OpenLAPDataSet;
import de.rwthaachen.openlap.exceptions.OpenLAPDataColumnException;
import de.ude.openlap.xapi.dto.OpenLapDataConverter;
import de.ude.openlap.xapi.model.Lrs;
import de.ude.openlap.xapi.repo.LrsRepo;

@Service
public class LrsServiceImp implements LrsService {

	@Autowired
	private LrsRepo lrsRepo;

	@Override
	public OpenLAPDataSet listOfLrsByOrganization(ObjectId organizationId)
			throws OpenLAPDataColumnException, JSONException {
		ArrayList listOfLrsIds = new ArrayList();
		ArrayList listOfLrsTitles = new ArrayList();

		for (Lrs lrs : lrsRepo.findLrsByOrganizationsId(organizationId)) {
			listOfLrsIds.add(lrs.getId());
			listOfLrsTitles.add(lrs.getTitle());
		}
		OpenLapDataConverter dataConveter = new OpenLapDataConverter();
		dataConveter.SetOpenLapDataColumn("LrsIds", OpenLAPColumnDataType.Text, true, listOfLrsIds);
		dataConveter.SetOpenLapDataColumn("LrsTitles", OpenLAPColumnDataType.Text, true, listOfLrsTitles);
		return dataConveter.getDataSet();
	}

}
