package com.service.drone.batch;

import com.service.drone.data.BatchStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
public class Batch {
    @Id
    @SequenceGenerator(
            name = "batch_sequence",
            sequenceName = "batch_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "batch_sequence"
    )
    private Long id;
    private String droneSerial;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BatchStatus status;

    public Batch(Long id) {
        this.id = id;
    }

    public Batch(Long id, String droneSerial, LocalDateTime startTime, LocalDateTime endTime, BatchStatus status) {
        this.id = id;
        this.droneSerial = droneSerial;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "id=" + id +
                ", droneSerial='" + droneSerial + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                '}';
    }

    public Batch() {
    }

    public Batch(String droneSerial) {
        this.droneSerial = droneSerial;
        this.status=BatchStatus.Open;
        this.startTime=LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDroneSerial() {
        return droneSerial;
    }

    public void setDroneSerial(String droneSerial) {
        this.droneSerial = droneSerial;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public BatchStatus getStatus() {
        return status;
    }

    public void setStatus(BatchStatus status) {
        this.status = status;
    }
}

