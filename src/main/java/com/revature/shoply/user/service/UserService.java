package com.revature.shoply.user.service;

import com.revature.shoply.models.User;
import com.revature.shoply.user.DTO.IncomingUserDTO;
import com.revature.shoply.user.DTO.OutgoingUserDTO;
import com.revature.shoply.user.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User findUserByIdAndValidate(UUID userId){
        if(userId == null) throw new IllegalArgumentException("User ID cannot be blank!");

        Optional<User> foundUser = userDAO.findById(userId);

        if(foundUser.isEmpty()) throw new IllegalArgumentException("No user found by ID: " + userId);

        return foundUser.get();
    }


    public OutgoingUserDTO getUserInfo(UUID userId){
        User foundUser = findUserByIdAndValidate(userId);

        return new OutgoingUserDTO(
                foundUser.getId(),
                foundUser.getUsername(),
                foundUser.getEmail(),
                foundUser.getFirstName(),
                foundUser.getLastName(),
                foundUser.getPhoneNumber(),
                foundUser.getRole()
        );
    }

    public OutgoingUserDTO updateUser(UUID userId, IncomingUserDTO incomingUser){

        if(incomingUser.getUsername() == null || incomingUser.getUsername().isBlank()){
            throw new IllegalArgumentException("Username cannot be blank or null");
        }

        if(incomingUser.getEmail() == null || incomingUser.getEmail().isBlank()){
            throw new IllegalArgumentException("User email cannot be blank or null");
        }

        if(incomingUser.getFirstname() == null || incomingUser.getFirstname().isBlank()){
            throw new IllegalArgumentException("User first name cannot be blank or null");
        }

        if(incomingUser.getLastname() == null || incomingUser.getLastname().isBlank()){
            throw new IllegalArgumentException("User last name cannot be blank or null");
        }

        if(incomingUser.getPhoneNumber() == null || incomingUser.getPhoneNumber().isBlank()){
            throw new IllegalArgumentException("User phone number cannot be blank or null");
        }

        User foundUser = findUserByIdAndValidate(userId);

        foundUser.setUsername(incomingUser.getUsername());
        foundUser.setEmail(incomingUser.getEmail());
        foundUser.setFirstName(incomingUser.getFirstname());
        foundUser.setLastName(incomingUser.getLastname());
        foundUser.setPhoneNumber(incomingUser.getPhoneNumber());

        User newUser = userDAO.save(foundUser);

        return new OutgoingUserDTO(
                newUser.getId(),
                newUser.getUsername(),
                newUser.getEmail(),
                newUser.getFirstName(),
                newUser.getLastName(),
                newUser.getPhoneNumber(),
                newUser.getRole()
        );
    }


    public boolean updateUserPassword(UUID userId, String oldPassword, String newPassword){
        if(oldPassword == null || oldPassword.isBlank()){
            throw new IllegalArgumentException("Old password cannot be blank or null");
        }
        if(newPassword == null || newPassword.isBlank()){
            throw new IllegalArgumentException("New password cannot be blank or null");
        }

        User foundUser = findUserByIdAndValidate(userId);

        if(foundUser.getPassword().equals(oldPassword)) {
           foundUser.setPassword(newPassword);
           userDAO.save(foundUser);
           return true;
        } else {
            throw new IllegalArgumentException("Old password is wrong!");
        }
    }
}
