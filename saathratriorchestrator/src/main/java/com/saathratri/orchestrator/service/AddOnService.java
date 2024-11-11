package com.saathratri.orchestrator.service;

import org.springframework.stereotype.Service;

import com.saathratri.orchestrator.domain.AddOn;
import com.saathratri.orchestrator.repository.AddOnRepository;

import java.util.List;

@Service
public class AddOnService {

    private final AddOnRepository addOnRepository;

    public AddOnService(AddOnRepository addOnRepository) {
        this.addOnRepository = addOnRepository;
    }

    public List<AddOn> getAllAddOns() {
        return addOnRepository.findAll();
    }
}
