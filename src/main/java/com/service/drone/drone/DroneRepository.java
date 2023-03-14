package com.service.drone.drone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository <Drone, Long>{

    @Query("SELECT s FROM Drone s WHERE s.serial=?1")
    Optional<Drone> findDroneBySerial(String serial);
    @Query("SELECT s FROM Drone s WHERE s.state=?1")
    Optional<List<Drone>> findDroneByStatus(State state);

}
