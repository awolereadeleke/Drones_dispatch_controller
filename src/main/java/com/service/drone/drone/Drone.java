package com.service.drone.drone;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

@Entity
@Table
public class Drone {
    @Id
    @SequenceGenerator(
            name = "drone_sequence",
            sequenceName = "drone_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "drone_sequence"
    )
    private Long id;
    private String serial;
    private Model model;
    private int weight;
    private int battery;
    private  State state;

    public Drone() {
    }

    public Drone(Long id) {
        this.id = id;
    }

    public Drone(String serial, Model model, int weight, int battery, State state) {
        this.serial = serial;
        this.model = model;
        this.weight = weight;
        this.battery = battery;
        this.state = state;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "id=" + id +
                ", serial=" + serial +
                ", model=" + model +
                ", weight=" + weight +
                ", battery=" + battery +
                ", state=" + state +
                '}';
    }
}

enum Model{
    Lightweight,
    Middleweight,
    Cruiserweight,
    Heavyweight;
}
enum State{
    IDLE,
    LOADING,
    LOADED,
    DELIVERING,
    DELIVERED,
    RETURNING
}