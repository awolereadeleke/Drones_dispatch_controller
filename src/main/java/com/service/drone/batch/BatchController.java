package com.service.drone.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/batch")
public class BatchController {
    private final BatchService batchService;
    @Autowired
    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }
    @GetMapping(path = {"", "/"})
    public List<Batch> getBatches(){
        return batchService.getBatches();
    }
    @PostMapping(path = {"save", "save/"})
    public void saveBatch(@RequestBody Batch batch){
        batchService.saveBatch(batch);
        System.out.println(batch);
    }
}
