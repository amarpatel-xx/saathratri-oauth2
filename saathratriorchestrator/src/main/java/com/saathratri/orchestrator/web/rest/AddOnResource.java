package com.saathratri.orchestrator.web.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saathratri.orchestrator.domain.AddOn;
import com.saathratri.orchestrator.service.AddOnService;

import java.util.List;

@RestController
@RequestMapping("/api/add-ons")
public class AddOnResource {

    private final AddOnService addOnService;

    public AddOnResource(AddOnService addOnService) {
        this.addOnService = addOnService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<AddOn> getAllAddOns() {
        return addOnService.getAllAddOns();
    }
}
