package com.service.drone.medication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class MedicationService {
    private final MedicationRepository medicationRepository;
    @Autowired
    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }
    public List<Medication> getMedications(){
        return medicationRepository.findAll();
    }
    public List<Medication> getBatchMedications(Long batchId){
        Optional<List<Medication>> exists=medicationRepository.findByBatchId(batchId);
        if(!exists.isPresent()){
            throw new IllegalStateException("Invalid batch Id");
        }
        return exists.get();
    }

    public void saveMedication(Medication medication){
        boolean namePettern= Pattern.matches("[a-zA-Z0-9_-]+", medication.getName());
        if(!namePettern){
            throw new IllegalStateException("Medication name can only contain letters, numbers, -, _");
        }
        boolean codePettern= Pattern.matches("[A-Z0-9_-]+", medication.getCode());
        if(!codePettern){
            throw new IllegalStateException("Medication code can only contain upper case letters, underscore and numbers");
        }
        medicationRepository.save(medication);
    }
}
