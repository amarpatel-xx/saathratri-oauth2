package com.saathratri.orchestrator.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saathratri.orchestrator.domain.AddOn;
import com.saathratri.orchestrator.service.AddOnService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/add-ons")
public class AddOnResource {

    private static final Logger log = LoggerFactory.getLogger(AddOnResource.class);

    private final AddOnService addOnService;

    public AddOnResource(AddOnService addOnService) {
        this.addOnService = addOnService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public Flux<AddOn> getAllAddOns() {
        log.debug("Getting all add-ons");
        return Flux.fromIterable(addOnService.getAllAddOns());
    }
}
