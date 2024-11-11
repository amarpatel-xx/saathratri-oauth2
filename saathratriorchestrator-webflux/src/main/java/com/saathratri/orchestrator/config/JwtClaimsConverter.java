package com.saathratri.orchestrator.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtClaimsConverter implements Converter<Jwt, Flux<GrantedAuthority>> {

    @Override
    public Flux<GrantedAuthority> convert(Jwt jwt) {
        return Flux.fromIterable(extractRealmRoles(jwt));
    }

    private Collection<GrantedAuthority> extractRealmRoles(Jwt jwt) {
        Map<String, Object> keycloakRealmAccess = jwt.getClaim("realm_access");
        Collection<String> auth0Roles = jwt.getClaim("https://www.jhipster.tech/roles");

        if (keycloakRealmAccess != null) {
            Object rolesObj = keycloakRealmAccess.get("roles");
            if (rolesObj instanceof Collection<?>) {
                Collection<String> roles = ((Collection<?>) rolesObj).stream()
                        .filter(role -> role instanceof String)
                        .map(role -> (String) role)
                        .collect(Collectors.toSet());
                return roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());
            }
        } else if (auth0Roles != null) {
            return auth0Roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }

        return Set.of(); // Return an empty set if no roles are found
    }
}



