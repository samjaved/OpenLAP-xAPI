package de.ude.openlap.xapi.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.rwthaachen.openlap.dataset.OpenLAPColumnDataType;
import de.rwthaachen.openlap.dataset.OpenLAPDataSet;
import de.rwthaachen.openlap.exceptions.OpenLAPDataColumnException;
import de.ude.openlap.xapi.dto.OpenLapDataConverter;
import de.ude.openlap.xapi.model.Person;
import de.ude.openlap.xapi.repo.PersonRepo;


@RestController
@RequestMapping("/v1/personas/")
public class PersonasController {
	@Autowired
	private PersonRepo personasRepo;

	/**
	 * 
	 * @return JsonArray of all the persons
	 * @throws IOException
	 * @throws OpenLAPDataColumnException
	 */
	@PreAuthorize("hasRole('site_admin')")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public OpenLAPDataSet listOfPersonNamesByOrganization(@RequestParam("OrganizationId") ObjectId OrganizationId)
			throws IOException, OpenLAPDataColumnException {
		ArrayList listOfPerson = new ArrayList();
		// ObjectId id = new ObjectId(OrganizationId);

		for (Person person : personasRepo.findPersonNamesByOrganization(OrganizationId)) {
			// room contains an element of rooms
			listOfPerson.add(person.getName());
		}

		OpenLapDataConverter dataConveter = new OpenLapDataConverter();
		dataConveter.SetOpenLapDataColumn("PersonNames", OpenLAPColumnDataType.Text, true,
				listOfPerson);
		
		return dataConveter.getDataSet();

	}
}
