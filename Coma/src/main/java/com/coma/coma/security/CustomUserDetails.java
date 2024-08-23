package com.coma.coma.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private String name;
    private int groupId;
    private int userId;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, String name, int groupId, int userId, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.groupId = groupId;
        this.userId = userId;
        this.authorities = authorities;
    }

    public String getName() {
        return name;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getUserId() {
        return userId;
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
        return true;
    }
}
