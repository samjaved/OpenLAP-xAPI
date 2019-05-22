package de.ude.openlap.xapi.controller;

import java.io.IOException;
import java.util.ArrayList;

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
import de.ude.openlap.xapi.model.Lrs;
import de.ude.openlap.xapi.repo.LrsRepo;

@RestController
@RequestMapping("/v1/lrs/")
public class LrsController {

	@Autowired
	private LrsRepo lrsRepo;

	@PreAuthorize("hasRole('site_admin')")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public OpenLAPDataSet listOfLrsByOrganization(@RequestParam("OrganizationId") String OrganizationId)
			throws IOException, OpenLAPDataColumnException
	{
		ArrayList listOfLrsIds = new ArrayList();
		ArrayList listOfLrsTitles = new ArrayList();

		for (Lrs lrs : lrsRepo.findAll()) {
			listOfLrsIds.add(lrs.getId());
			listOfLrsTitles.add(lrs.getTitle());
		}
		OpenLapDataConverter dataConveter = new OpenLapDataConverter();
		dataConveter.SetOpenLapDataColumn("LrsIds", OpenLAPColumnDataType.Text, true, listOfLrsIds);
		dataConveter.SetOpenLapDataColumn("LrsTitles", OpenLAPColumnDataType.Text, true,
				listOfLrsTitles);
		return dataConveter.getDataSet();
	}
}
