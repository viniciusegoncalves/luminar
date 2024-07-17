package com.generation.luminar.security;

import com.generation.luminar.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class UserDetailsImpl implements UserDetails  {

    private static final Long serialVersionUId = 1L;

    private String userName;
    private String password;
    private List<GrantedAuthority>authorities;


    public UserDetailsImpl(User user){
        this.userName = user.getName();
        this.password = user.getPassword();
    }

    public UserDetailsImpl(){

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
        return userName;
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
        return UserDetails.super.isEnabled();
    }
}