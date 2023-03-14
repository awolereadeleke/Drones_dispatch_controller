package com.service.drone.drone;

import com.service.drone.batch.Batch;
import com.service.drone.batch.BatchService;
import com.service.drone.data.BatchStatus;
import com.service.drone.data.Serial;
import com.service.drone.data.Token;
import com.service.drone.medication.Medication;
import com.service.drone.medication.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class DroneService {
    private  final DroneRepository droneRepository;
    private final BatchService batchService;
    private final MedicationService medicationService;
    private final AuditRepository auditRepository;
    private  final int serialMaxLen=100;
    @Autowired
    public DroneService(DroneRepository droneRepository, BatchService batchService,MedicationService medicationService, AuditRepository auditRepository) {
        this.droneRepository = droneRepository;
        this.batchService = batchService;
        this.medicationService = medicationService;
        this.auditRepository = auditRepository;
    }

    public List<Drone> getDrones(){
        return  droneRepository.findAll();
    }

    public void saveDrone(Drone drone) {
        if(drone.getSerial().length() > serialMaxLen){
            throw new IllegalStateException("serial number is more than the maximum length allowed");
        }
        Optional<Drone> exist=droneRepository.findDroneBySerial(drone.getSerial());
        if(exist.isPresent()){
            throw new IllegalStateException("Drone with serial number already exist");
        }
        if(drone.getWeight() > 500){
            throw new IllegalStateException("Weight limit for a drone is 500gr");
        }
        if(drone.getWeight() < 0){
            throw new IllegalStateException("Weight limit cannot be less than 0");
        }
        if(drone.getBattery() > 100){
            throw new IllegalStateException("Battery capacity cannot be more than 100");
        }
        if(drone.getBattery() < 0){
            throw new IllegalStateException("Battery capacity cannot be less than 0");
        }
        droneRepository.save(drone);
        System.out.println(drone);
    }
    public Token loadingDrone(Serial serial){
        if(serial.getSerial() == null || serial.getSerial().trim()==""){
            throw new IllegalStateException("Serial cannot be empty");
        }
        Optional<Drone> exist=droneRepository.findDroneBySerial(serial.getSerial());
        if(!exist.isPresent()){
            throw new IllegalStateException("No Drone with serial number "+ serial.getSerial() +"found");
        }
        Drone drone=exist.get();
        if (drone.getState()==State.DELIVERING){
            throw new IllegalStateException("Drone is already delivering");
        }
        if(drone.getBattery() < 25){
            throw new IllegalStateException("Drone battery is bellow 25%");
        }
        Batch batch=new Batch(drone.getSerial());
        Batch b=batchService.saveBatch(batch);
        drone.setState(State.LOADING);
        droneRepository.save(drone);
        System.out.println(b);
        String base64 = Base64.getEncoder().encodeToString(b.getId().toString().getBytes());
        Token token=new Token(base64);
        return token;
    }
    private Long deoced(String token){
        String dec=new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8);
        System.out.println(dec);
        return Long.parseLong(dec);
    }
    public boolean loadDrone(String token, Medication medication) {
        Long batchId=deoced(token);
        Batch batch=batchService.getBatchById(batchId);
        if(batch.getStatus()== BatchStatus.Close){
            throw new IllegalStateException("this batch is closed");
        }
        Optional<Drone> droneoptional=droneRepository.findDroneBySerial(batch.getDroneSerial());
        if(!droneoptional.isPresent()){
            throw new IllegalStateException("Unable to get drone details");
        }
        List<Medication> medications= medicationService.getBatchMedications(batchId);

        Drone drone=droneoptional.get();
        int existingWeight=0;
        for(Medication existingMed:medications){
            existingWeight+= existingMed.getWeight();
        }
        if(existingWeight + medication.getWeight() > drone.getWeight()){
            throw new IllegalStateException("this drona can only take " + (drone.getWeight()-existingWeight) +"gr of load more");
        }
        medication.setBatchId(batch.getId());
        medicationService.saveMedication(medication);

        return true;
    }

    public List<Medication> getload(String token) {
        Long batchId=deoced(token);
        Batch batch=batchService.getBatchById(batchId);
        if(batch.getStatus()== BatchStatus.Close){
            throw new IllegalStateException("this batch is closed");
        }
        Optional<Drone> droneoptional=droneRepository.findDroneBySerial(batch.getDroneSerial());
        if(!droneoptional.isPresent()){
            throw new IllegalStateException("Unable to get drone details");
        }
        List<Medication> medications= medicationService.getBatchMedications(batchId);
        return medications;
    }

    public List<Drone> getloading() {
        Optional<List<Drone>> exist=droneRepository.findDroneByStatus(State.LOADING);
        return exist.get();
    }

    public int getBattery(Serial serial) {
        Optional<Drone> exist=droneRepository.findDroneBySerial(serial.getSerial());
        if(!exist.isPresent())throw new IllegalStateException("Drone with serial number "+ serial.getSerial() + " does not exist");
        return exist.get().getBattery();
    }
    @Scheduled(fixedRate = 10000)
    public void batteryCheck(){
        List<Drone> drones=droneRepository.findAll();
        for (Drone drone: drones){
            BatteryAudit audit=new BatteryAudit(drone.getSerial(), drone.getBattery());
            auditRepository.save(audit);
        }
        System.out.println("Checking");
    }
    public List<BatteryAudit> getAudit(Serial serial){
        Optional<List<BatteryAudit>> exists=auditRepository.findBySerial(serial.getSerial());
        if(!exists.isPresent()){
            throw new IllegalStateException("No data available");
        }
        return exists.get();
    }
}
