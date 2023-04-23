package com.example.shinigami;

import java.util.HashMap;
import java.util.Map;

public class Device {
    private int deviceId;
    private String deviceName;
    private String deviceDesc;
    private boolean isWorking;
    private HashMap<String, String> deviceStatuses = new HashMap<>();
//        user.put("id", u.getUserId());
//        user.put("firstName ", u.getFirstName());
//        user.put("lastName", u.getLastName());
//        user.put("dob", u.getDob());

    public Device(int deviceId,String deviceName, String deviceDesc, boolean isWorking) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceDesc = deviceDesc;
        this.isWorking = isWorking;
    }

    public Device(int deviceId, String deviceNamme, String deviceDesc) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceDesc = deviceDesc;
        this.isWorking = false;
    }

    public void  setStatus(String statusName, String status) {
        deviceStatuses.put(statusName, status);
    }
}
