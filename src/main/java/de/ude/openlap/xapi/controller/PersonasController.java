package de.ude.openlap.xapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.ude.openlap.xapi.repo.PersonasRepo;

@Controller
public class PersonasController {
	@Autowired
	private PersonasRepo personasRepo;

	@RequestMapping("/openlap/personas/")
	@ResponseBody
	public String index() throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String personaslist = gson.toJson(personasRepo.findAll());
		return personaslist;

	}
}
