package de.ude.openlap.xapi.repo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import de.ude.openlap.xapi.model.Person;

public interface PersonRepo extends MongoRepository<Person, String> {

	@Query(value = "{'organisation':?0 }", fields = "{'_id':1,'name':1}")
	List<Person> findPersonNamesByOrganization(ObjectId id);

}
