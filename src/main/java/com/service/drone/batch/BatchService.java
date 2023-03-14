package com.service.drone.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatchService {
    private final BatchRepository batchRepository;
    @Autowired
    public BatchService(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }
    public List<Batch> getBatches(){
        return batchRepository.findAll();
    }
    public  Batch getBatchById(Long batchId){
        Optional<Batch> exists=batchRepository.findById(batchId);
        if(!exists.isPresent()){
            throw new IllegalStateException("Load Batch does not exist");
        }
        return exists.get();
    }
    public Batch saveBatch(Batch batch){
        Optional<Batch> exits=batchRepository.findDroneStatusBySerial(batch.getDroneSerial());
        if(!exits.isPresent()){
            //throw new IllegalStateException("Selected Drone is already in loading state");
            batchRepository.save(batch);
            return batch;
        }
        System.out.println(exits.isPresent());
        return exits.get();
    }
}
