package de.ude.openlap.xapi.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import de.ude.openlap.xapi.dto.Verb;
import de.ude.openlap.xapi.model.Statement;

public interface StatementRepo extends MongoRepository<Statement, String> {


	@Query(value = "{'?0.?1':{ $exists: true  } }", fields = "{ '?0.?1':1}")
	List<Verb> findAllVerbs(String objectname, String url);

}
