package de.ude.openlap.xapi.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.ude.openlap.xapi.model.Personas;

public interface PersonasRepo extends MongoRepository<Personas, String> {

}
