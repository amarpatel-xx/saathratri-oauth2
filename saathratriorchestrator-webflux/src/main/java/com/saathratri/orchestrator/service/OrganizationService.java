package com.saathratri.orchestrator.service;


import org.springframework.stereotype.Service;

import com.saathratri.orchestrator.domain.Organization;
import com.saathratri.orchestrator.repository.OrganizationRepository;

import java.util.List;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }
}
