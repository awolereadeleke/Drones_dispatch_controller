package com.service.drone.data;

public class Serial {
    private String serial;

    public Serial(String serial) {
        this.serial = serial;
    }

    public Serial() {
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @Override
    public String toString() {
        return "Serial{" +
                "serial='" + serial + '\'' +
                '}';
    }
}
