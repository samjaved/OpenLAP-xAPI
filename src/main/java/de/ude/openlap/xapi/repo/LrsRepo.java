package de.ude.openlap.xapi.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.ude.openlap.xapi.model.Lrs;

public interface LrsRepo extends MongoRepository<Lrs, String> {

}
