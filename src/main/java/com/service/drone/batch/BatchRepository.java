package com.service.drone.batch;

import com.service.drone.drone.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
    @Query("SELECT s FROM Batch s WHERE s.droneSerial=?1 and s.status=0")
    Optional<Batch> findDroneStatusBySerial(String serial);

}
