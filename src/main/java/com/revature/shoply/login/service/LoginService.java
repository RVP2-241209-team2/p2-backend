package com.revature.shoply.login.service;

import com.revature.shoply.login.repository.LoginDAO;
import com.revature.shoply.models.DTOs.LoginDTO;
import com.revature.shoply.models.DTOs.OutgoingUserDTO;
import com.revature.shoply.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * LoginService class handles business logic related to all user logins
 */
@Service
public class LoginService {
    private final LoginDAO loginDAO;

    @Autowired
    public LoginService(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }


    /**
     * Authenticates a user based on the provided login credentials.
     *
     * This method takes in a {@link LoginDTO} object containing the username and password,
     * validates the input, and retrieves the corresponding user from the data source.
     * If the credentials are valid, it returns an {@link OutgoingUserDTO} object with
     * the user's details. Otherwise, it throws an exception.
     *
     * @param loginDTO A {@link LoginDTO} object containing the username and password for authentication.
     * @return An {@link OutgoingUserDTO} object containing the authenticated user's details,
     *         such as ID, username, first name, last name, and role.
     * @throws IllegalArgumentException If the username or password is blank, or if no user is found
     *                                  with the provided credentials.
     */
    public OutgoingUserDTO login(LoginDTO loginDTO){
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        if(username.isBlank()) throw new IllegalArgumentException("Username cannot be blank!");
        if(password.isBlank()) throw new IllegalArgumentException("Password cannot be blank!");

        User user = loginDAO.findByUsernameAndPassword(
            username,
            password
        );

        if(user == null) throw new IllegalArgumentException("No user found with those credentials");

        return new OutgoingUserDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
    }
}
