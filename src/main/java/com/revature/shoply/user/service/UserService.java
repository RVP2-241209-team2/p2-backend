package com.revature.shoply.user.service;

import com.revature.shoply.models.Address;
import com.revature.shoply.models.PaymentDetails;
import com.revature.shoply.models.User;
import com.revature.shoply.user.DTO.*;
import com.revature.shoply.user.repository.AddressDAO;
import com.revature.shoply.user.repository.PaymentMethodDAO;
import com.revature.shoply.Repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
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
        if(userId == null) throw new IllegalArgumentException("User ID cannot be blank!");

        Optional<User> foundUser = userDAO.findById(userId);

        if(foundUser.isEmpty()) throw new IllegalArgumentException("No user found by ID: " + userId);

        return foundUser.get();
    }

    public Address findAddressByIdAndValidate(UUID addressId){
        if(addressId == null) throw new IllegalArgumentException("Address ID cannot be null");
        Optional<Address> foundAddress = addressDAO.findById(addressId);
        if(foundAddress.isEmpty()) throw new IllegalArgumentException("No Address found with AddressID: " + addressId);

        return foundAddress.get();
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



    //Addresses

    public List<Address> getAddresses(UUID userId){
        User foundUser = findUserByIdAndValidate(userId);
        return addressDAO.findByUserId(userId);
    }

    public Address addAddress(UUID userId, IncomingAddressDTO address){
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

        User foundUser = findUserByIdAndValidate(userId);
        Address foundAddress = findAddressByIdAndValidate(addressId);
        if(foundAddress != null) {
            if (!addressDAO.existsById(addressId)) {
                throw new IllegalArgumentException("Address with ID " + addressId + " does not exist.");
            }

            addressDAO.deleteById(addressId);

            return true;
        }
        return false;
    }



    //Payment methods

    public List<PaymentDetails> getPaymentMethods(UUID userId){
        User foundUser = findUserByIdAndValidate(userId);
        return paymentMethodDAO.findByUserId(userId);
    }


    public PaymentDetails addPayMethod(UUID userId, IncomingPayDetailsDTO payMethod){
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
        User foundUser = findUserByIdAndValidate(userId);
        Address foundAddress = findAddressByIdAndValidate(payMethod.getAddressId());

        if(payMethodId == null) throw new IllegalArgumentException("Payment Method ID cannot be null");
        Optional<PaymentDetails> foundPayMethod = paymentMethodDAO.findById(payMethodId);
        if(foundPayMethod.isEmpty()) throw new IllegalArgumentException("No Payment Method found with Payment Method ID: " + payMethodId);

        PaymentDetails newPayMethod = foundPayMethod.get();

        newPayMethod.setCardNumber(payMethod.getCardNumber());
        newPayMethod.setCardHolderName(payMethod.getCardHolderName());
        newPayMethod.setExpireDate(payMethod.getExpireDate());
        newPayMethod.setAddress(foundAddress);
        newPayMethod.setUser(foundUser);
        newPayMethod.setIsDefault(payMethod.getIsDefault());

        return paymentMethodDAO.save(newPayMethod);
    }


    public boolean deletePayMethod(UUID userId, UUID payMethodId){

        User foundUser = findUserByIdAndValidate(userId);
        if(payMethodId == null) throw new IllegalArgumentException("Payment Method ID cannot be null");

        if (!paymentMethodDAO.existsById(payMethodId)) {
            throw new IllegalArgumentException("Payment Method with ID " + payMethodId + " does not exist.");
        }

        paymentMethodDAO.deleteById(payMethodId);

        return true;
    }
}
