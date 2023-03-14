package com.service.drone.drone;

import com.service.drone.data.Serial;
import com.service.drone.data.Token;
import com.service.drone.medication.Medication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/drone")
public class DroneController {
    private final DroneService droneService;
    @Autowired
    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @GetMapping(path = {"", "/"})
    public List<Drone> getDrones(){
        return droneService.getDrones();
    }
    @PostMapping(path = {"save", "save/"})
    public void saveDrone(@RequestBody Drone drone){
        droneService.saveDrone(drone);
    }
    @PostMapping(path = {"loadtoken", "loadtoken/"})
    public Token loadingDrone(@RequestBody Serial serial){
        return droneService.loadingDrone(serial);
    }

    @PostMapping(path = {"load", "load/"})
    public boolean loadDrone(@RequestHeader(value = "Authorization") String token,@RequestBody Medication medication){
        return droneService.loadDrone(token,medication);
    }
    @GetMapping(path = {"getload", "getload/"})
    public List<Medication> getload(@RequestHeader(value = "Authorization") String token){
        return droneService.getload(token);
    }
    @GetMapping(path = {"loadingstatus", "loadingstatus/"})
    public List<Drone> getloading(){
        return droneService.getloading();
    }
    @PostMapping(path = {"battery", "battery/"})
    public int getBattery(@RequestBody Serial serial){
        return droneService.getBattery(serial);
    }

    @PostMapping(path = {"audit", "audit/"})
    public List<BatteryAudit> getAudit(@RequestBody Serial serial){
        return droneService.getAudit(serial);
    }


}
