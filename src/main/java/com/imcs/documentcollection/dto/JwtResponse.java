package com.imcs.documentcollection.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String username;
    private String email;
    private String shortHandName;
    private String name;
    private String manager;
    private List<String> roles;

    public JwtResponse(String accessToken, String username, String email, String shortHandName, List<String> roles, String name, String manager) {
        this.accessToken = accessToken;
        this.username = username;
        this.email = email;
        this.shortHandName = shortHandName;
        this.roles = roles;
        this.name=name;
        this.manager=manager;
    }

}
