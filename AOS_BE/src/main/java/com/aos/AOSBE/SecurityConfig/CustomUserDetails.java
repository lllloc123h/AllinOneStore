package com.aos.AOSBE.SecurityConfig;

import com.aos.AOSBE.Entity.Accounts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Optional;

public class CustomUserDetails implements UserDetails {
    Optional<Accounts> user ;
    Collection<? extends GrantedAuthority> authorities ;
    public CustomUserDetails(Optional<Accounts> user, Collection<? extends GrantedAuthority> authorities) {
        super();
        this.user = user;
        this.authorities = authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.get().getPassword();
    }

    @Override
    public String getUsername() {
        return user.get().getEmail();
    }
}
