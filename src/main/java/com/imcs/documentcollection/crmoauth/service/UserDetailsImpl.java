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

    private String username;

    private String email;

    private String name;

    private String manager;

    private String shortHandName;

    @JsonIgnore
    private String password;

    private boolean enabled;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String username, String email, String name ,String manager, String password, boolean enabled, String shortHandName,
                           Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.shortHandName = shortHandName;
        this.authorities = authorities;
        this.name=name;
        this.manager=manager;
    }

    public static UserDetailsImpl build(UserProfile user) {

        String authority = user.getAuthority();
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>() {{add(new SimpleGrantedAuthority(authority));}};

        StringBuilder shortHandName = new StringBuilder();
        String[] nameArray = user.getFullName().split(" ");
        for(String s: nameArray){
            shortHandName.append(s.charAt(0));
        }
        return new UserDetailsImpl(
                user.getUsername(),
                user.getEmail(),
                user.getFirstName()+ " " +user.getLastName(),
                user.getManager(),
                user.getPassword(),
                user.isEnabled(),
                shortHandName.toString(),
                authorities);
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    public String getShortHandName() {
        return shortHandName;
    }

    public void setShortHandName(String shortHandName) {
        this.shortHandName = shortHandName;
    }

    public String getName() {
        return name;
    }

    public void setName(String shortHandName) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(username, user.username);
    }
}
