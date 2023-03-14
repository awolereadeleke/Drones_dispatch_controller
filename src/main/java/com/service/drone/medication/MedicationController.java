package com.service.drone.medication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/medication")

public class MedicationController {
    private final MedicationService medicationService;
    @Autowired
    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }
    @GetMapping(path={"","/"})
    public List<Medication> getMedications(){
        return medicationService.getMedications();
    }

}
