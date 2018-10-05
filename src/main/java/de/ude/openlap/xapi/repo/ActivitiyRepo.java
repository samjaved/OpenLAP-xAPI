package de.ude.openlap.xapi.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import de.ude.openlap.xapi.model.Activitiy;

public interface ActivitiyRepo extends MongoRepository<Activitiy, String> {

	@Query(value = "{ '?0.?1' : '?2' }")
	List<Activitiy> findByObjectNameAndProperty(String Objcet, String Parameter, String Value);

	@Query(value = "{ '?0.?1.?2' : '?3' }")
	List<Activitiy> findByObjects(String Objcet, String Secondobjcet, String Parameter, int Value);

	List<Activitiy> findByActivityId(String activityid);

	List<Activitiy> findByType(String type);

}
