package com.service.drone.drone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AuditRepository extends JpaRepository<BatteryAudit, Long>{
    @Query("SELECT s FROM BatteryAudit s WHERE s.serial=?1")
    Optional<List<BatteryAudit>> findBySerial(String serial);

}
