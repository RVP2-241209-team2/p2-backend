package com.revature.shoply.user.controller;

import com.revature.shoply.user.DTO.IncomingUserDTO;
import com.revature.shoply.user.DTO.OutgoingUserDTO;
import com.revature.shoply.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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

}
