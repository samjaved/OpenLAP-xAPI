package de.ude.openlap.xapi.service;

import java.io.IOException;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import de.rwthaachen.openlap.dataset.OpenLAPColumnDataType;
import de.rwthaachen.openlap.dataset.OpenLAPDataSet;
import de.rwthaachen.openlap.exceptions.OpenLAPDataColumnException;
import de.ude.openlap.xapi.dto.OpenLapDataConverter;
import de.ude.openlap.xapi.model.Organization;
import de.ude.openlap.xapi.model.User;
import de.ude.openlap.xapi.repo.OrganizationRepo;
import de.ude.openlap.xapi.repo.UserRepo;

@Service
public class OrganizationServiceImp implements OrganizationService {
	@Autowired
	private OrganizationRepo organizationRepo;

	@Autowired
	private UserRepo userRepo;
	@Override
	public OpenLAPDataSet getOrganizationForLoggedUser(Authentication authentication)
			throws IOException, OpenLAPDataColumnException {
		User user = userRepo.findByEmail(authentication.getName());

		ObjectId userId = new ObjectId(user.getId());
		ArrayList listOfOrganizationIds = new ArrayList();
		ArrayList listOfOrganizationNames = new ArrayList();
		for (Organization organization : organizationRepo.findOrganizationsByUserId(userId)) {
			listOfOrganizationIds.add(organization.getId());
			listOfOrganizationNames.add(organization.getName());
		}
		OpenLapDataConverter dataConveter = new OpenLapDataConverter();
		dataConveter.SetOpenLapDataColumn("OrganizationIds", OpenLAPColumnDataType.Text, true, listOfOrganizationIds);
		dataConveter.SetOpenLapDataColumn("OrganizationNames", OpenLAPColumnDataType.Text, true,
				listOfOrganizationNames);
		return dataConveter.getDataSet();


	}

}
