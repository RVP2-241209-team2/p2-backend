package com.revature.shoply.user.controller;

import com.revature.shoply.models.Address;
import com.revature.shoply.models.PaymentDetails;
import com.revature.shoply.user.DTO.*;
import com.revature.shoply.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    ResponseEntity<OutgoingUserDTO> getUserInfo(@PathVariable UUID userId){
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

    @PutMapping("/{userId}")
    ResponseEntity<OutgoingUserDTO> updateUserInfo(@RequestBody IncomingUserDTO user, @PathVariable UUID userId){
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

    @PatchMapping("/{userId}/password")
    ResponseEntity<Map<String, String>> updateUserPassword(@RequestBody Map<String, Object> password, @PathVariable UUID userId){
        String oldPassword = (String) password.get("oldPassword");
        String newPassword = (String) password.get("newPassword");

        boolean isSuccess = userService.updateUserPassword(userId, oldPassword, newPassword);

        if(isSuccess) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Password updated successfully!"));
        }

        return ResponseEntity.ok(Collections.singletonMap("message", "Something went wrong!"));
    }


    @GetMapping("/{userId}/addresses")
    ResponseEntity<List<Address>> getAddresses(@PathVariable UUID userId){
        return ResponseEntity.ok(userService.getAddresses(userId));
    }

    @PostMapping("/{userId}/addresses")
    ResponseEntity<Address> addAddress(@RequestBody IncomingAddressDTO address, @PathVariable UUID userId) {
        return ResponseEntity.ok(userService.addAddress(userId, address));
    }

    @PutMapping("/{userId}/addresses/{addressId}")
    ResponseEntity<Address> updateAddress(@RequestBody IncomingAddressDTO address, @PathVariable UUID userId, @PathVariable UUID addressId){
        return ResponseEntity.ok(userService.updateAddress(userId, addressId, address));
    }

    @DeleteMapping("/{userId}/addresses/{addressId}")
    ResponseEntity<Map<String, String>> deleteAddress(@PathVariable UUID addressId){
        boolean isSuccess = userService.deleteAddress(addressId);

        if(isSuccess) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Address deleted!"));
        }

        return ResponseEntity.ok(Collections.singletonMap("message", "Something went wrong!"));
    }



    @GetMapping("/{userId}/payment-methods")
    ResponseEntity<List<PaymentDetails>> getPaymentMethods(@PathVariable UUID userId){
        return ResponseEntity.ok(userService.getPaymentMethods(userId));
    }


    @PostMapping("/{userId}/payment-methods")
    ResponseEntity<PaymentDetails> addPaymentMethod(@RequestBody IncomingPayDetailsDTO payment, @PathVariable UUID userId) {
        return ResponseEntity.ok(userService.addPayMethod(userId, payment));
    }

    @PutMapping("/{userId}/payment-methods/{payMethodId}")
    ResponseEntity<PaymentDetails> updatePaymentMethod(@RequestBody IncomingPayDetailsDTO payment, @PathVariable UUID userId, @PathVariable UUID payMethodId){
        return ResponseEntity.ok(userService.updatePayMethod(userId, payMethodId, payment));
    }

    @DeleteMapping("/{userId}/payment-methods/{payMethodId}")
    ResponseEntity<Map<String, String>> deletePaymentMethod(@PathVariable UUID payMethodId){
        boolean isSuccess = userService.deletePayMethod(payMethodId);

        if(isSuccess) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Payment method deleted!"));
        }

        return ResponseEntity.ok(Collections.singletonMap("message", "Something went wrong!"));
    }

}
