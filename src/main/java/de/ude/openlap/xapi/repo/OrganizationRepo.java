package de.ude.openlap.xapi.repo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import de.ude.openlap.xapi.model.Organization;

/**
 * MongoRepository to handle data access layer of
 * OrganizationController
 *
 */
public interface OrganizationRepo extends MongoRepository<Organization, String> {
	/**
	 * Query to get organization for logged user
	 * 
	 * @param userid
	 * @return id and name of the oragnization
	 */
	@Query(value = "{'owner':?0 }", fields = "{'_id':1,'name':1}")
	List<Organization> findOrganizationsByUserId(ObjectId id);
}
