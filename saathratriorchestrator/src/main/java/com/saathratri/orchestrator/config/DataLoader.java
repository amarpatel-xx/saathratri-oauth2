package com.saathratri.orchestrator.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.saathratri.orchestrator.domain.AddOn;
import com.saathratri.orchestrator.domain.Organization;
import com.saathratri.orchestrator.repository.AddOnRepository;
import com.saathratri.orchestrator.repository.OrganizationRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadOrganizationsData(OrganizationRepository repository) {
        return args -> {
            repository.save(new Organization("Organization 1", "Description 1"));
            repository.save(new Organization("Organization 2", "Description 2"));
        };
    }

    @Bean
    CommandLineRunner loadAddOnsData(AddOnRepository addOnRepository) {
        return args -> {
            addOnRepository.save(new AddOn("Add-on 1", "Description 1", 10.0, true, "1", "Service"));
            addOnRepository.save(new AddOn("Add-on 2", "Description 2", 20.0, true, "1", "Service"));
            addOnRepository.save(new AddOn("Add-on 3", "Description 3", 35.0, true, "1", "Service"));
            addOnRepository.save(new AddOn("Add-on 4", "Description 4", 40.0, true, "1", "Service"));
        };
    }
}

