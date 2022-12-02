package com.imcs.documentcollection.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class JwtResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String email;
    private String name;

    public JwtResponse(String accessToken, String email,String name) {
        this.accessToken = accessToken;
        this.email = email;
        this.name=name;
    }

}
