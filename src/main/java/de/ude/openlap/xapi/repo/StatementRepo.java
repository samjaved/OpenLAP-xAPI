package de.ude.openlap.xapi.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.ude.openlap.xapi.model.Statements;

public interface StatementsRepo extends MongoRepository<Statements, String> {

}
