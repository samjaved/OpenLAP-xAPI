package de.ude.openlap.xapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.ude.openlap.xapi.repo.FullActivitiesRepo;

@Controller
public class ActivityController {


	@Autowired
	private FullActivitiesRepo activityRepo;

	@RequestMapping("/activities")
	@ResponseBody
	public String getAllActivities() throws IOException {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String activiteslist = gson.toJson(activityRepo.findAll());
		return activiteslist;

	}

	@RequestMapping("/activity/{Id}")
	@ResponseBody
	public String getActivitiesById(@PathVariable("Id") String Id) throws IOException {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String activiteslist = gson.toJson(activityRepo.findById(Id));
		return activiteslist;

	}

	@RequestMapping("/activity/activityId/{activityId}")
	@ResponseBody
	public String getActivitiesByActivityId(@PathVariable("activityId") String activityId) throws IOException {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String activiteslist = gson.toJson(activityRepo.findByActivityId(activityId));
		return activiteslist;


	}

	@RequestMapping("/activity/{objectName}/{propertyName}/{propertyValue}")
	@ResponseBody
	public String getActivitiesByOBjectNameAndProperty(@PathVariable("objectName") String objcetName,
			@PathVariable("propertyName") String propertyName, @PathVariable("propertyValue") String propertyValue)
			throws IOException {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String activiteslist = gson
				.toJson(activityRepo.findByObjectNameAndProperty(objcetName, propertyName, propertyValue));
		return activiteslist;

	}

}
