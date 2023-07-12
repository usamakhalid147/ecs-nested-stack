package com.acima.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author LavKumar
 * The type Jwt authentication.
 */
public class JwtAuthentication implements Authentication {

    private final String token;

    /**
     * Instantiates a new Jwt authentication.
     *
     * @param token the token
     */
    public JwtAuthentication(String token) {
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    /**
     * Returns the name of this principal.
     *
     * @return the name of this principal.
     */
    @Override
    public String getName() {
        return null;
    }

    // Implement the required methods of the Authentication interface
    // For example, getPrincipal(), getCredentials(), isAuthenticated(), etc.
}