package com.revature.shoply.user.controller;

import com.revature.shoply.models.Address;
import com.revature.shoply.models.PaymentDetails;
import com.revature.shoply.security.JwtUtil;
import com.revature.shoply.user.DTO.*;
import com.revature.shoply.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers/users")
@CrossOrigin
public class UserController {

    private final JwtUtil jwtUtil;

    private final UserService userService;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Secured("STORE_OWNER")
    @GetMapping("/all")
    ResponseEntity<List<OutgoingUserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/my-info")
    ResponseEntity<OutgoingUserDTO> getUserInfo(@RequestHeader("Authorization") String token){
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

    @PutMapping("/my-info")
    ResponseEntity<OutgoingUserDTO> updateUserInfo(@RequestBody IncomingUserDTO user, @RequestHeader("Authorization") String token){
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

    @DeleteMapping("/delete")
    ResponseEntity<Map<String, String>> deleteUser(@RequestHeader("Authorization") String token){
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        boolean isSuccess = userService.deleteUser(userId);

        if (isSuccess) {
            return ResponseEntity.ok(Collections.singletonMap("message", "User deleted!"));
        }

        return ResponseEntity.ok(Collections.singletonMap("message", "Something went wrong!"));
    }

    @PatchMapping("/my-info/password")
    ResponseEntity<Map<String, String>> updateUserPassword(@RequestBody Map<String, Object> password, @RequestHeader("Authorization") String token){
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        String oldPassword = (String) password.get("oldPassword");
        String newPassword = (String) password.get("newPassword");

        boolean isSuccess = userService.updateUserPassword(userId, oldPassword, newPassword);

        if (isSuccess) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Password updated successfully!"));
        }

        return ResponseEntity.ok(Collections.singletonMap("message", "Something went wrong!"));
    }


    @GetMapping("/my-info/addresses")
    ResponseEntity<List<Address>> getAddresses(@RequestHeader("Authorization") String token){
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        return ResponseEntity.ok(userService.getAddresses(userId));
    }

    @PostMapping("/my-info/addresses")
    ResponseEntity<Address> addAddress(@RequestBody IncomingAddressDTO address, @RequestHeader("Authorization") String token){
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        return ResponseEntity.ok(userService.addAddress(userId, address));
    }

    @PutMapping("/my-info/addresses/{addressId}")
    ResponseEntity<Address> updateAddress(@RequestBody IncomingAddressDTO address, @PathVariable UUID addressId, @RequestHeader("Authorization") String token){
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        return ResponseEntity.ok(userService.updateAddress(userId, addressId, address));
    }

    @DeleteMapping("/my-info/addresses/{addressId}")
    ResponseEntity<Map<String, String>> deleteAddress(@PathVariable UUID addressId, @RequestHeader("Authorization") String token){
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        boolean isSuccess = userService.deleteAddress(userId, addressId);

        if (isSuccess) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Address deleted!"));
        }

        return ResponseEntity.ok(Collections.singletonMap("message", "Something went wrong!"));
    }



    @GetMapping("/my-info/payment-methods")
    ResponseEntity<List<PaymentDetails>> getPaymentMethods(@RequestHeader("Authorization") String token){
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        return ResponseEntity.ok(userService.getPaymentMethods(userId));
    }


    @PostMapping("/my-info/payment-methods")
    ResponseEntity<PaymentDetails> addPaymentMethod(@RequestBody IncomingPayDetailsDTO payment, @RequestHeader("Authorization") String token){
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        return ResponseEntity.ok(userService.addPayMethod(userId, payment));
    }

    @PutMapping("/my-info/payment-methods/{payMethodId}")
    ResponseEntity<PaymentDetails> updatePaymentMethod(@RequestBody IncomingPayDetailsDTO payment, @PathVariable UUID payMethodId, @RequestHeader("Authorization") String token){
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        return ResponseEntity.ok(userService.updatePayMethod(userId, payMethodId, payment));
    }

    @DeleteMapping("/my-info/payment-methods/{payMethodId}")
    ResponseEntity<Map<String, String>> deletePaymentMethod(@RequestHeader("Authorization") String token, @PathVariable UUID payMethodId){
        UUID userId = UUID.fromString(jwtUtil.extractUserId(token.substring(7)));
        boolean isSuccess = userService.deletePayMethod(userId, payMethodId);

        if (isSuccess) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Payment method deleted!"));
        }

        return ResponseEntity.ok(Collections.singletonMap("message", "Something went wrong!"));
    }

}
