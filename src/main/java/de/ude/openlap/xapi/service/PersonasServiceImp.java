package de.ude.openlap.xapi.service;

import java.io.IOException;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.rwthaachen.openlap.dataset.OpenLAPColumnDataType;
import de.rwthaachen.openlap.dataset.OpenLAPDataSet;
import de.rwthaachen.openlap.exceptions.OpenLAPDataColumnException;
import de.ude.openlap.xapi.dto.OpenLapDataConverter;
import de.ude.openlap.xapi.model.Person;
import de.ude.openlap.xapi.repo.PersonRepo;

@Service
public class PersonasServiceImp implements PersonasService {
	@Autowired
	private PersonRepo personasRepo;

	@Override
	public OpenLAPDataSet listOfPersonNamesByOrganization(ObjectId OrganizationId)
			throws IOException, OpenLAPDataColumnException {
		ArrayList listOfPerson = new ArrayList();
		ArrayList listOfPersonIds = new ArrayList();
		// ObjectId id = new ObjectId(OrganizationId);

		for (Person person : personasRepo.findPersonNamesByOrganization(OrganizationId)) {

			listOfPersonIds.add(person.getId());
			listOfPerson.add(person.getName());
		}

		OpenLapDataConverter dataConveter = new OpenLapDataConverter();
		dataConveter.SetOpenLapDataColumn("PersonIds", OpenLAPColumnDataType.Text, true, listOfPersonIds);
		dataConveter.SetOpenLapDataColumn("PersonNames", OpenLAPColumnDataType.Text, true, listOfPerson);

		return dataConveter.getDataSet();
	}

}
