package com.revature.shoply.user.service;

import com.revature.shoply.models.Address;
import com.revature.shoply.models.PaymentDetails;
import com.revature.shoply.models.User;
import com.revature.shoply.user.DTO.*;
import com.revature.shoply.user.repository.AddressDAO;
import com.revature.shoply.user.repository.PaymentMethodDAO;
import com.revature.shoply.repositories.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserDAO userDAO;
    private final AddressDAO addressDAO;
    private final PaymentMethodDAO paymentMethodDAO;


    @Autowired
    public UserService(UserDAO userDAO, AddressDAO addressDAO, PaymentMethodDAO paymentMethodDAO) {
        this.userDAO = userDAO;
        this.addressDAO = addressDAO;
        this.paymentMethodDAO = paymentMethodDAO;
    }

    public User findUserByIdAndValidate(UUID userId){
        log.info("Finding user by id and validating");

        if(userId == null) throw new IllegalArgumentException("User ID cannot be blank!");

        Optional<User> foundUser = userDAO.findById(userId);

        if(foundUser.isEmpty()) throw new IllegalArgumentException("No user found by ID: " + userId);

        return foundUser.get();
    }

    public Address findAddressByIdAndValidate(UUID addressId){
        log.info("Finding address by Id: {}, and validating", addressId);

        if(addressId == null) throw new IllegalArgumentException("Address ID cannot be null");
        Optional<Address> foundAddress = addressDAO.findById(addressId);
        if(foundAddress.isEmpty()) throw new IllegalArgumentException("No Address found with AddressID: " + addressId);

        return foundAddress.get();
    }

    public PaymentDetails findPayMethodByIdAndValidate(UUID payMethodId){
        log.info("Finding payment method by Id: {}, and validating", payMethodId);

        if(payMethodId == null) throw new IllegalArgumentException("Payment Method ID cannot be null");
        Optional<PaymentDetails> foundPayMethod = paymentMethodDAO.findById(payMethodId);
        if(foundPayMethod.isEmpty()) throw new IllegalArgumentException("No Payment Method found with Payment Method ID: " + payMethodId);

        return foundPayMethod.get();
    }


    public OutgoingUserDTO getUserInfo(UUID userId){
        log.info("Getting User Info");

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
        log.info("Updating user");

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

        User usernameCheck = userDAO.findByUsername(incomingUser.getUsername());
        if(usernameCheck != null && usernameCheck.getId() != userId){
            throw new IllegalArgumentException("Username " + incomingUser.getUsername() + " is already taken. Try a different username");
        }

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


    public boolean deleteUser(UUID userId){
        log.info("Deleting user");

        User foundUser = findUserByIdAndValidate(userId);

        if(foundUser == null) {
            return false;
        } else {
            userDAO.deleteById(userId);
            return true;
        }
    }


    public boolean updateUserPassword(UUID userId, String oldPassword, String newPassword){
        log.info("Updating user password");

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



    //Addresses

    public List<Address> getAddresses(UUID userId){
        log.info("Getting users addresses");

        User foundUser = findUserByIdAndValidate(userId);
        return addressDAO.findByUserId(userId);
    }

    public Address addAddress(UUID userId, IncomingAddressDTO address){
        log.info("Adding address for user");

        User foundUser = findUserByIdAndValidate(userId);
        Address newAddress = new Address(
                null,
                foundUser,
                address.getAddressLine1(),
                address.getAddressLine2(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getCountry(),
                address.getType()
        );

        return addressDAO.save(newAddress);
    }

    public Address updateAddress(UUID userId, UUID addressId, IncomingAddressDTO address){
        log.info("Updating address for user");

        User foundUser = findUserByIdAndValidate(userId);

        if(addressId == null) throw new IllegalArgumentException("Address ID cannot be null");
        Optional<Address> foundAddress = addressDAO.findById(addressId);
        if(foundAddress.isEmpty()) throw new IllegalArgumentException("No Address found with AddressID: " + addressId);

        Address newAddress = foundAddress.get();

        newAddress.setAddressLine1(address.getAddressLine1());
        newAddress.setAddressLine2(address.getAddressLine2());
        newAddress.setUser(foundUser);
        newAddress.setCity(address.getCity());
        newAddress.setState(address.getState());
        newAddress.setZipCode(address.getZipCode());
        newAddress.setCountry(address.getCountry());
        newAddress.setType(address.getType());


        return addressDAO.save(newAddress);
    }

    public boolean deleteAddress(UUID userId, UUID addressId){
        log.info("Deleting address with Id: {}, for user", addressId);

        User foundUser = findUserByIdAndValidate(userId);
        Address foundAddress = findAddressByIdAndValidate(addressId);

        for(PaymentDetails paymentDetails: foundAddress.getPaymentDetails()){
            paymentDetails.setAddress(null);
            paymentMethodDAO.save(paymentDetails);
        }

        addressDAO.deleteById(addressId);

        return true;
    }



    //Payment methods

    public List<PaymentDetails> getPaymentMethods(UUID userId){
        log.info("Getting payment methods for user");

        User foundUser = findUserByIdAndValidate(userId);
        return paymentMethodDAO.findByUserId(userId);
    }


    public PaymentDetails addPayMethod(UUID userId, IncomingPayDetailsDTO payMethod){
        log.info("Adding payment method for user");

        User foundUser = findUserByIdAndValidate(userId);
        Address foundAddress = findAddressByIdAndValidate(payMethod.getAddressId());

        PaymentDetails newPaymentMethod = new PaymentDetails(
                null,
                foundUser,
                payMethod.getCardNumber(),
                payMethod.getCardHolderName(),
                payMethod.getExpireDate(),
                foundAddress,
                payMethod.getIsDefault()
        );

        return paymentMethodDAO.save(newPaymentMethod);
    }

    public PaymentDetails updatePayMethod(UUID userId, UUID payMethodId, IncomingPayDetailsDTO payMethod){
        log.info("Updating payment method with id: {}, for user", payMethodId);

        User foundUser = findUserByIdAndValidate(userId);
        Address foundAddress = findAddressByIdAndValidate(payMethod.getAddressId());
        PaymentDetails foundPayMethod = findPayMethodByIdAndValidate(payMethodId);

        foundPayMethod.setCardNumber(payMethod.getCardNumber());
        foundPayMethod.setCardHolderName(payMethod.getCardHolderName());
        foundPayMethod.setExpireDate(payMethod.getExpireDate());
        foundPayMethod.setAddress(foundAddress);
        foundPayMethod.setUser(foundUser);
        foundPayMethod.setIsDefault(payMethod.getIsDefault());

        return paymentMethodDAO.save(foundPayMethod);
    }


    public boolean deletePayMethod(UUID userId, UUID payMethodId){
        log.info("Deleting payment method");

        User foundUser = findUserByIdAndValidate(userId);
        PaymentDetails foundPayMethod = findPayMethodByIdAndValidate(payMethodId);

        foundPayMethod.setAddress(null);
        paymentMethodDAO.save(foundPayMethod);
        paymentMethodDAO.deleteById(payMethodId);

        return true;
    }
}
