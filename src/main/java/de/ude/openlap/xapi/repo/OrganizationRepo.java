package de.ude.openlap.xapi.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.ude.openlap.xapi.model.Organization;

public interface OrganizationRepo extends MongoRepository<Organization, String> {

}
