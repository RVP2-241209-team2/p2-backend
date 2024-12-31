package com.revature.shoply.models.DTOs;

/**
 * Data Transfer Object (DTO) for encapsulating user login credentials.
 *
 * This class represents a simple object used for transferring login information,
 * specifically the username and password, between different layers of the application.
 * It provides constructors, getters, setters, and a `toString` method for easy usage.
 *
 * This class is typically used in authentication workflows.
 */
public class LoginDTO {
    private String username;
    private String password;


    //Constructors

    public LoginDTO() {
    }

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }


    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    //ToString
    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
