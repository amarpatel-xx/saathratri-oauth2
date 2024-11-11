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
            addOnRepository.save(new AddOn("Early Check-in", "Check-in earlier than standard time", 20.0, true, "1", "Service"));
            addOnRepository.save(new AddOn("Very Early Check-in", "Check-in significantly earlier than standard time", 40.0, true, "1", "Service"));
            addOnRepository.save(new AddOn("Late Check-out", "Check-out later than standard time", 25.0, true, "1", "Service"));
            addOnRepository.save(new AddOn("Very Late Check-out", "Check-out significantly later than standard time", 50.0, true, "1", "Service"));
        };
    }
}

