package com.example.shinigami;

public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String dob;

    public User(String userId, String firstName, String lastName, String dob) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public String getUserId () {
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
