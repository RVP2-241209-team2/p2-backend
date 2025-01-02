package com.revature.shoply.user.DTO;

import com.revature.shoply.models.enums.UserRole;

import java.util.UUID;


public class IncomingUserDTO {
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String phoneNumber;


    //Constructors

    public IncomingUserDTO() {
    }

    public IncomingUserDTO(String username, String email, String firstname, String lastname, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
    }

// Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //ToString
    @Override
    public String toString() {
        return "OutgoingUserDTO{" +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
