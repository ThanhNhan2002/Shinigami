package com.example.shinigami;

import java.util.ArrayList;
import java.util.Collections;

public class House {
    private int houseId;
    private String houseAddress;
    private ArrayList<User> userList;


    public House(int houseId, String houseAddress, ArrayList<User> userList) {
        this.houseId = houseId;
        this.houseAddress = houseAddress;
        Collections.copy(userList, this.userList);
        //this.userList = userList;
    }

    public House(int houseId, String houseAddress) {
        this.houseId = houseId;
        this.houseAddress = houseAddress;
        this.userList = new ArrayList<User>();
    }

    public int getHouseId () {
        return this.houseId;
    }

    public String getHouseAddress() {
        return this.houseAddress;
    }

    public boolean addUser(User user) {
        this.userList.add(user);
        return true;
    }

    public ArrayList<User> getUsers() {
        return this.userList;
    }

}
