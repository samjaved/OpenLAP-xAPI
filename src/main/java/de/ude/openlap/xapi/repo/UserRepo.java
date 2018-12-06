package de.ude.openlap.xapi.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.ude.openlap.xapi.model.User;

;

public interface UserRepo extends MongoRepository<User, String> {
	User findByEmail(String email);
}
