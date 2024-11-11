package com.saathratri.orchestrator.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

import com.saathratri.orchestrator.domain.Organization;
import com.saathratri.orchestrator.service.OrganizationService;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationResource {

    private static final Logger log = LoggerFactory.getLogger(OrganizationResource.class);
    
    private final OrganizationService organizationService;

    public OrganizationResource(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Flux<Organization> getAllOrganizations() {
        return Flux.fromIterable(organizationService.getAllOrganizations());
    }

    // @GetMapping
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // public Flux<Organization> getAllOrganizations(@AuthenticationPrincipal Mono<Authentication> authMono) {
    //     return authMono.doOnNext(auth -> 
    //             log.debug("Granted Authorities: {}", auth.getAuthorities())
    //         )
    //         .thenMany(Flux.fromIterable(organizationService.getAllOrganizations()));
    // }

    // @GetMapping
    // public Flux<Organization> getAllOrganizations() {
    //     return Flux.fromIterable(organizationService.getAllOrganizations());
    // }
}
