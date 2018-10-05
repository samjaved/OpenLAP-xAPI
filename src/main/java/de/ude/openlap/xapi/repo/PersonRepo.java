package de.ude.openlap.xapi.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.ude.openlap.xapi.model.Person;

public interface PersonRepo extends MongoRepository<Person, String> {

}
