package com.imcs.documentcollection.service;

import com.imcs.documentcollection.model.UserProfile;
import com.imcs.documentcollection.model.UserRequest;
import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    List<UserProfile> getAllUsers();

    @Transactional
    String createUser(UserRequest user) throws Exception;

    UserProfile getByUsername(String username);

    @Transactional
    String updateUser(UserRequest user) throws Exception;

    boolean deleteUser(String email) throws Exception;

//    User assignTeam(String username, Long teamId) throws Exception;
}
