package com.imcs.documentcollection.repository;

import com.imcs.documentcollection.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface UserRepository extends JpaRepository<UserProfile, String> {
	//public User findOne();
	
	@Query(value  = 
			"SELECT authority FROM authorities " +
			"WHERE username=:username"
			, nativeQuery = true)
			public String getRoleOfUser(
		   		@Param("username") String username);
	
	@Modifying
	@Query(value  =
	"DELETE FROM users WHERE "+
    "username =:uname "
	, nativeQuery = true)
	public void deleteUser(@Param("uname") String uname);
	
	@Query(value  = 
			"SELECT * FROM users " +
			"WHERE manager IS NULL"
			, nativeQuery = true)
			public List<UserProfile> findAllWhereManagerNull();
	

	Optional<UserProfile> findByUsername(String username);
	Optional<UserProfile> findByEmail(String email);

	@Query(value  =
			"SELECT username FROM users " +
					"WHERE manager = :manager"
			, nativeQuery = true)
	List<String> findAllByManagerIs(String manager);

	List<UserProfile> findAllByManagerIsNullAndEnabledIsTrue();
	List<UserProfile> findAllByEnabledIsTrueAndManagerIs(String manager);
	@Query(value  = "SELECT u.* FROM users u inner join login_logout l on u.username=l.username and l.end is null where DATE_FORMAT(l.start,'%m-%d-%Y') = DATE_FORMAT(:todayDate,'%m-%d-%Y')", nativeQuery = true)
	List<UserProfile> findAllNotLogoutUsers(@Param("todayDate") String todayDate);

	@Query(value="select x.* from users x LEFT JOIN users y ON x.username = y.manager where y.manager is null", nativeQuery = true)
	List<UserProfile> findAllUsersWhoAreNotManagers();
}
