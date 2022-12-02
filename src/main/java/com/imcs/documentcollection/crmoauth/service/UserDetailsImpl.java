package com.imcs.documentcollection.crmoauth.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imcs.documentcollection.model.UserProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String email;

    private String name;

    @JsonIgnore
    private String password;

    private boolean enabled;

    public UserDetailsImpl(String email, String name ,String password, boolean enabled) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.name=name;
    }

    public static UserDetailsImpl build(UserProfile user) {

        StringBuilder shortHandName = new StringBuilder();
        String[] nameArray = user.getFirstName().split(" ");
        for(String s: nameArray){
            shortHandName.append(s.charAt(0));
        }
        return new UserDetailsImpl(
                user.getEmail(),
                user.getFirstName()+ " " +user.getMiddleName()+" " +user.getLastName(),
                user.getPassword(),
                user.isEnabled());
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(email, user.email);
    }
}
