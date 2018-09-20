package de.ude.openlap.xapi.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import de.ude.openlap.xapi.model.FullActivities;

public interface FullActivitiesRepo extends MongoRepository<FullActivities, String> {

	@Query(value = "{ '?0.?1' : ?2 }")
	List<FullActivities> findByObjectNameAndProperty(String Objcet, String Parameter, String Value);

	@Query(value = "{ '?0.?1.?2' : ?3 }")
	List<FullActivities> findByObjects(String Objcet, String Secondobjcet, String Parameter, int Value);

	List<FullActivities> findByActivityId(String activityid);

	List<FullActivities> findByType(String type);

}
