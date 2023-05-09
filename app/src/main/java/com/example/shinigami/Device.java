package com.example.shinigami;

import java.util.HashMap;
import java.util.Map;

public class Device {
    private int deviceId;
    private String deviceName;
    private String deviceDesc;
    private boolean isWorking;
    private HashMap<String, String> deviceStatuses = new HashMap<>();

    public Device(int deviceId,String deviceName, String deviceDesc, boolean isWorking) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceDesc = deviceDesc;
        this.isWorking = isWorking;
    }

    public Device(int deviceId, String deviceName, String deviceDesc) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceDesc = deviceDesc;
        this.isWorking = true;
    }

    public void addStatus(String statusName, String status) {
        deviceStatuses.put(statusName, status);
    }

    public int getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceDesc() {
        return deviceDesc;
    }

    public boolean getIsWorking() {
        return isWorking;
    }

    public HashMap<String, String> getDeviceStatuses() {
        return deviceStatuses;
    }
}
