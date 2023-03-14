package com.service.drone.drone;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class BatteryAudit {
        @Id
        @SequenceGenerator(
                name = "audit_sequence",
                sequenceName = "audit_sequence",
                allocationSize = 1
        )
        @GeneratedValue(
                strategy = GenerationType.SEQUENCE,
                generator = "audit_sequence"
        )
        private Long id;
        String serial;
        int battery;
        LocalDateTime auditTime;

    public BatteryAudit(Long id) {
        this.id = id;
    }

    public BatteryAudit() {
    }

    public BatteryAudit(String serial, int battery, LocalDateTime auditTime) {
        this.serial = serial;
        this.battery = battery;
        this.auditTime = auditTime;
    }

    public BatteryAudit(String serial, int battery) {
        this.serial = serial;
        this.battery = battery;
        this.auditTime=LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerial() {
            return serial;
        }

        public void setSerial(String serial) {
            this.serial = serial;
        }

        public int getBattery() {
            return battery;
        }

        public void setBattery(int battery) {
            this.battery = battery;
        }

        public LocalDateTime getAuditTime() {
            return auditTime;
        }

        public void setAuditTime(LocalDateTime auditTime) {
            this.auditTime = auditTime;
        }

        @Override
        public String toString() {
            return "batteryAudit{" +
                    "serial='" + serial + '\'' +
                    ", battery=" + battery +
                    ", auditTime=" + auditTime +
                    '}';
        }
}