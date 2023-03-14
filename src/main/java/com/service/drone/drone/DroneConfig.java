package com.service.drone.drone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DroneConfig {
    @Bean
    CommandLineRunner commandLineRunner(DroneRepository droneRepository){
        return args -> {
            List<Drone> drones= List.of(
                    new Drone("DR_01",Model.Heavyweight,200, 50, State.IDLE),
                    new Drone("DR_02",Model.Cruiserweight,100, 30, State.LOADED)
            );
            droneRepository.saveAll(drones);
        };
    }
}
