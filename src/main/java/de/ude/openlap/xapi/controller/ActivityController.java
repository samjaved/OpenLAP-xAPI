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

import de.ude.openlap.xapi.repo.ActivitiyRepo;

@RestController
@RequestMapping("/v1/activity/")
public class ActivityController {


	@Autowired
	private ActivitiyRepo activityRepo;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String getAllActivities() throws IOException {

		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		String activiteslist = gson.toJson(activityRepo.findAll());
		String value = new String(activiteslist);
		return value;

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

	@RequestMapping(value = "/show/activitesByObjectName/{objectName}", method = RequestMethod.GET)
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
	public String getActivitiesByOBjectsName(@PathVariable("objectName") String objectname,
			@RequestParam("secondObjectName") String secondobjectname,
			@RequestParam("propertyName") String propertyName, @RequestParam("propertyValue") int propertyValue)
			throws IOException {

		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		String activiteslist = gson
				.toJson(activityRepo.findByObjects(objectname, secondobjectname, propertyName, propertyValue));
		return activiteslist;

	}

	@RequestMapping(value = "/show/extentions/metadata", method = RequestMethod.GET)
	@ResponseBody
	public String getActivitiesContextualFields(@RequestParam("objectName") String objectName,
			@RequestParam("extensionsUrl") String extensionsUrl)
			throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		String activiteslist = gson
				.toJson(activityRepo.findContextualFieldsByExtensionUrl(objectName, extensionsUrl));
		return activiteslist;
	}

	//@RequestMapping(value = "/show/extentions/metadata", method = RequestMethod.POST)
	//@ResponseBody
	/*public String getActivitiesByQuery(@RequestBody ExtentionParameters jsonString)
			throws IOException {
		System.out.println(jsonString.getObj1());
		String query = "{'extensions.http://collide&46;info/xapi/gitAction.name':'gitExtension'}";
		System.out.println(query);
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		String activiteslist = gson
				.toJson(activityRepo.findActivitesByQuery(query));
		//return activiteslist;
	}*/


}
