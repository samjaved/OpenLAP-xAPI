package de.ude.openlap.xapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.ude.openlap.xapi.repo.FullActivitiesRepo;

@RestController
@RequestMapping("/v1/activity/")
public class ActivityController {


	@Autowired
	private FullActivitiesRepo activityRepo;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String getAllActivities() throws IOException {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String activiteslist = gson.toJson(activityRepo.findAll());
		return activiteslist;

	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	@ResponseBody
	public String getActivitiesById(@RequestParam("Id") String Id) throws IOException {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String activiteslist = gson.toJson(activityRepo.findById(Id));
		return activiteslist;

	}

	@RequestMapping(value = "/show/activitesByActivtyId", method = RequestMethod.GET)
	@ResponseBody
	public String getActivitiesByActivityId(@RequestParam("activityId") String activityId) throws IOException {
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String activiteslist = gson.toJson(activityRepo.findByActivityId(activityId));
		return activiteslist;


	}

	@RequestMapping(value = "/show/activitesByType", method = RequestMethod.GET)
	@ResponseBody
	public String getActivitiesByType(@RequestParam("type") String type) throws IOException {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String activiteslist = gson.toJson(activityRepo.findByType(type));
		return activiteslist;

	}

	@RequestMapping(value = "/show/{objectName}", method = RequestMethod.GET)
	@ResponseBody
	public String getActivitiesByOBjectNameAndProperty(@PathVariable("objectName") String objcetName,
			@RequestParam("propertyName") String propertyName, @RequestParam("propertyValue") String propertyValue)
			throws IOException {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String activiteslist = gson
				.toJson(activityRepo.findByObjectNameAndProperty(objcetName, propertyName, propertyValue));
		return activiteslist;

	}

	@RequestMapping(value = "/show/metadata/{objectName}", method = RequestMethod.GET)
	@ResponseBody
	public String getActivitiesByOBjectsName(@PathVariable("objectName") String objcetName,
			@RequestParam("propertyName") String propertyName, @RequestParam("propertyValue") int propertyValue,
			@RequestParam("secondObjectName") String secondobjectname)
			throws IOException {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String activiteslist = gson
				.toJson(activityRepo.findByObjects(objcetName, secondobjectname, propertyName, propertyValue));
		return activiteslist;

	}

}
