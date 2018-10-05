package de.ude.openlap.xapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.ude.openlap.xapi.repo.OrganizationRepo;

@RestController
@RequestMapping("/v1/organizations/")
public class OrganizationController {
	@Autowired
	private OrganizationRepo organizationRepo;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String index() throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String personaslist = gson.toJson(organizationRepo.findAll());
		return personaslist;
	}
}
