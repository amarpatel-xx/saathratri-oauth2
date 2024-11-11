package com.saathratri.orchestrator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saathratri.orchestrator.domain.AddOn;

@Repository
public interface AddOnRepository extends JpaRepository<AddOn, Long> {
}