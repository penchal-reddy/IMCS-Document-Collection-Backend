package com.imcs.documentcollection.controller;

import com.imcs.documentcollection.model.UserProfile;
import com.imcs.documentcollection.model.UserRequest;
import com.imcs.documentcollection.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody UserRequest user, Authentication authentication) throws Exception {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/read")
    public ResponseEntity<List<UserProfile>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("get/{email}")
    public ResponseEntity<UserProfile> getByUsername(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.getByUsername(email));
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody UserRequest user, Authentication authentication) throws Exception {
        return ResponseEntity.ok(userService.updateUser(user));
    }

}
