package com.example.shinigami;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String dob;

    public User(int userId, String firstName, String lastName, String dob) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public int getUserId () {
        return this.userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getDob() {
        return this.dob;
    }

}
