package com.imcs.documentcollection.repository;

import com.imcs.documentcollection.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface UserRepository extends JpaRepository<UserProfile, String> {
	//public User findOne();
	Optional<UserProfile> findByEmail(String email);
}
