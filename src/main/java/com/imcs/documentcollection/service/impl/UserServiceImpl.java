package com.imcs.documentcollection.service.impl;

import com.imcs.documentcollection.dto.CRMUtil;
import com.imcs.documentcollection.model.UserProfile;
import com.imcs.documentcollection.model.UserRequest;
import com.imcs.documentcollection.repository.UserRepository;
import com.imcs.documentcollection.service.IEmailService;
import com.imcs.documentcollection.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    IEmailService emailService;

    @Override
    public List<UserProfile> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public String createUser(UserRequest user) throws Exception {
        UserProfile newUser = new UserProfile();
        BeanUtils.copyProperties(user, newUser);

        //create password
        String generatedPassword = CRMUtil.generatePassword(8);
        newUser.setPassword(passwordEncoder.encode(generatedPassword));

        newUser = userRepository.save(newUser);

        String emailSubject = "IMCS Document Collection Account Created";
        String emailContent = "Dear " + user.getFirstName() + " " + user.getLastName() + ",\n\n" + "Your account has been created for IMCS Document Collection portal. Please find your autogenerated password below.\n\n" + generatedPassword + "\n\nBelow are the steps to change your password.\n1. Click on the profile name on the rightmost corner of the screen.\n2. Click on change password.\n3. Enter the old password and new password and then click Save.";
        emailService.sendEmail(newUser.getEmail(), "", "", emailSubject, emailContent, "text/plain");
        return "User Registration Success";
    }

    @Override
    public UserProfile getByUsername(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public String updateUser(UserRequest user) throws Exception {
        if (user.getEmail() != null) {
            UserProfile userFromDB = userRepository.findByEmail(user.getEmail()).get();
            BeanUtils.copyProperties(user, userFromDB);
            userRepository.save(userFromDB);
            return "User Updated Success";
        }
        return "User Updated Failed";
    }

    @Override
    public boolean deleteUser(String email) throws Exception {
        if (email !=null) {
            UserProfile userFromDB = userRepository.findByEmail(email).get();
            userRepository.delete(userFromDB);
            return true;
        }
        throw new Exception("Invalid user data");
    }
}