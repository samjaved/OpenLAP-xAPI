package de.ude.openlap.xapi.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import de.ude.openlap.xapi.model.Activitiy;

public interface ActivitiyRepo extends MongoRepository<Activitiy, String> {

	@Query(value = "{ '?0.?1' : '?2' }")
	List<Activitiy> findByObjectNameAndProperty(String Object, String Parameter, String Value);

	@Query(value = "{ '?0.?1.?2' : '?3' }")
	List<Activitiy> findByObjects(String Object, String Secondobject, String Parameter, int Value);

	List<Activitiy> findByActivityId(String activityid);

	@Query(value = "{'?0.?1':{ $exists: true } }", fields = "{ 'extensions':1}")
	List<Activitiy> findContextualFieldsByExtensionUrl(String objectname, String url);

	/*
	 * @Query(value = "{?0}") List<Activitiy> findActivitesByQuery(String query);
	 */

	List<Activitiy> findByType(String type);


}
