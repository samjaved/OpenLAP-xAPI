package de.ude.openlap.xapi.controller;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.rwthaachen.openlap.dataset.OpenLAPDataSet;
import de.rwthaachen.openlap.exceptions.OpenLAPDataColumnException;
import de.ude.openlap.xapi.service.ActivityService;

@RestController
@RequestMapping("/v1/activity/")
public class ActivityController {

	@Autowired
	private ActivityService activityService;

	@PreAuthorize("hasRole('site_admin')")
	@RequestMapping(value = "/show/activites", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public OpenLAPDataSet activitiesList(@RequestParam("OrganizationId") ObjectId OrganizationId,
			@RequestParam("LrsId") ObjectId lrsId) throws OpenLAPDataColumnException, JSONException {

		return activityService.getActivities(OrganizationId, lrsId);
	}

	@PreAuthorize("hasRole('site_admin')")
	@RequestMapping(value = "/show/activitesExtensionContextValues", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public OpenLAPDataSet activitiesExtensionContextValues(@RequestParam("OrganizationId") ObjectId OrganizationId,
			@RequestParam("LrsId") ObjectId lrsId, @RequestParam("extensionId") String extensionId,
			@RequestParam("extensionContextKey") String extensionContextKey)
			throws IOException, JSONException, OpenLAPDataColumnException {

		return activityService.getActivitiesExtensionContextValues(OrganizationId, lrsId, extensionId,
				extensionContextKey);

	}




}
