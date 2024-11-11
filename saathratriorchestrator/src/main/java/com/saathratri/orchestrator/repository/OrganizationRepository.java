package com.saathratri.orchestrator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saathratri.orchestrator.domain.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
