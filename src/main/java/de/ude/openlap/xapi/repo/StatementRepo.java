package de.ude.openlap.xapi.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.ude.openlap.xapi.model.Statement;

public interface StatementRepo extends MongoRepository<Statement, String> {

}
