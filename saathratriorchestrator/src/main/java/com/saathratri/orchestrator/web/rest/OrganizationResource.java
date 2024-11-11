package com.saathratri.orchestrator.web.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saathratri.orchestrator.domain.Organization;
import com.saathratri.orchestrator.service.OrganizationService;

import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationResource {

    private final OrganizationService organizationService;

    public OrganizationResource(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Organization> getAllOrganizations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Granted Authorities: " + auth.getAuthorities());
        return organizationService.getAllOrganizations();
    }
}
