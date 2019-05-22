package de.ude.openlap.xapi.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.mongodb.DBObject;

import de.ude.openlap.xapi.dto.Verb;
import de.ude.openlap.xapi.model.Statement;

public interface StatementRepo extends MongoRepository<Statement, String> {


	@Query(value = "{'statement.verb':{ $exists: true  } }", fields = "{ 'id':0,'statement.verb':1}")
	List<Verb> findAllVerbs();

	@Query(value = "{'$and':[{'$or':?0},{'$and':?1}]}", fields = "?2")
	List<Statement> findDataByCustomQuery(DBObject queryOptionalObject, DBObject queryComplusoryObject,
			DBObject parametersToReceiveObject);

}
