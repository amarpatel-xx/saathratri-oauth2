package com.saathratri.orchestrator.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtClaimsConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
       var authorities = extractRealmRoles(jwt);
        return new JwtAuthenticationToken(jwt, authorities, getPrincipalFromClaim(jwt));
    }

    private String getPrincipalFromClaim(Jwt jwt) {
        var claimName = "preferred_username";
        return jwt.getClaim(claimName);
    }

    private Collection<GrantedAuthority> extractRealmRoles(Jwt jwt) {
        // Check if the 'realm_access' claim is present and extract roles
        Map<String, Object> keycloakRealmAccess = jwt.getClaim("realm_access");
        // Check if the Auth0 custom claim for roles is present and extract roles
        Collection<String> auth0Roles = jwt.getClaim("https://www.jhipster.tech/roles");

        if (keycloakRealmAccess != null) {
            Object rolesObj = keycloakRealmAccess.get("roles");

            Collection<String> roles = null;
            if (rolesObj instanceof Collection<?>) {
                roles = ((Collection<?>) rolesObj).stream()
                        .filter(role -> role instanceof String)
                        .map(role -> (String) role)
                        .collect(Collectors.toSet());

                if (roles != null) {
                    return roles.stream()
                                .map(role -> new SimpleGrantedAuthority(role))
                                .collect(Collectors.toSet());
                }
            }
        } else if (auth0Roles != null) {
            return auth0Roles.stream()
                             .map(role -> new SimpleGrantedAuthority(role))
                             .collect(Collectors.toSet());
        }
    
        // Return an empty set if no roles are found
        return Set.of();
    }
    
}