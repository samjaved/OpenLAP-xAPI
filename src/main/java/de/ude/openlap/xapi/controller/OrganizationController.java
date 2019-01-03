package de.ude.openlap.xapi.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.rwthaachen.openlap.dataset.OpenLAPColumnDataType;
import de.rwthaachen.openlap.dataset.OpenLAPDataSet;
import de.rwthaachen.openlap.exceptions.OpenLAPDataColumnException;
import de.ude.openlap.xapi.dto.OpenLapDataConverter;
import de.ude.openlap.xapi.model.Organization;
import de.ude.openlap.xapi.repo.OrganizationRepo;

@RestController
@RequestMapping("/v1/organizations/")
public class OrganizationController {
	@Autowired
	private OrganizationRepo organizationRepo;

	@PreAuthorize("hasRole('site_admin')")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public OpenLAPDataSet list() throws IOException, OpenLAPDataColumnException {
		ArrayList listOfOrganizationIds = new ArrayList();
		ArrayList listOfOrganizationNames = new ArrayList();
		for (Organization organization : organizationRepo.findAll()) {
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
