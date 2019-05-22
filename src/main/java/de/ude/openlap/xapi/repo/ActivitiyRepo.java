package de.ude.openlap.xapi.repo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import de.ude.openlap.xapi.model.Activitiy;

public interface ActivitiyRepo extends MongoRepository<Activitiy, String> {

	/*
	 * @Query(value = "{ '?0.?1' : '?2' }") List<Activitiy>
	 * findByObjectNameAndProperty(String Object, String Parameter, String Value);
	 * 
	 * @Query(value = "{ '?0.?1.?2' : '?3' }") List<Activitiy> findByObjects(String
	 * Object, String Secondobject, String Parameter, int Value);
	 * 
	 * List<Activitiy> findByActivityId(String activityid);
	 */

	@Query(value = "{'$and':[{'organisation':?0},{'lrs_id':?1}]}", fields = "{ '?2.?3.?4':1}")
	List<Activitiy> findContextualFieldValuesByExtensionUrlAndKey(ObjectId organizationId, ObjectId lrsId,
			String extension, String extensionId, String extensionContextKey);

	@Query(value = "{'$and':[{'organisation':?0},{'lrs_id':?1}]}")
	List<Activitiy> findActivitiesByOrganizationAndLrs(ObjectId organizationId, ObjectId lrsId);


}
