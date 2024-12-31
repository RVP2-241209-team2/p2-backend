package com.revature.shoply.models.DTOs;

import com.revature.shoply.models.enums.UserRole;

import java.util.UUID;


/**
 * Data Transfer Object (DTO) for representing user information sent from the server to the client.
 *
 * This class is used to encapsulate the details of a user after authentication or other user-related operations.
 * It includes the user's ID, username, first name, last name, and role.
 * This object is specifically designed for outgoing responses, ensuring sensitive information such as passwords
 * is excluded.
 */
public class OutgoingUserDTO {
    private UUID id;
    private String username;
    private String firstname;
    private String lastname;
    private UserRole role;


    //Constructors

    public OutgoingUserDTO() {
    }

    public OutgoingUserDTO(UUID id, String username, String firstname, String lastname, UserRole role) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }


    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }


    //ToString
    @Override
    public String toString() {
        return "OutgoingUserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", role=" + role +
                '}';
    }
}
