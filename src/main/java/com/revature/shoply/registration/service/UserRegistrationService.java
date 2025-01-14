package com.revature.shoply.registration.service;

import com.revature.shoply.models.User;
import com.revature.shoply.registration.dto.UserRegistrationRequestDTO;
import com.revature.shoply.registration.dto.UserRegistrationResponseDTO;
import com.revature.shoply.registration.repository.UserRegistrationDAO;
import com.revature.shoply.user.exception.UniqueConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserRegistrationService {

    private final UserRegistrationDAO registrationDAO;

    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRegistrationDAO registrationDAO, PasswordEncoder passwordEncoder) {
        this.registrationDAO = registrationDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRegistrationResponseDTO registerUser(UserRegistrationRequestDTO registrationRequestDTO) {
        validateRegistrationRequest(registrationRequestDTO);
        checkForDuplicateFields(registrationRequestDTO);
        User user = mapToUser(registrationRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = registrationDAO.save(user);
        return mapToResponseDto(savedUser);

    }

    private void validateRegistrationRequest(UserRegistrationRequestDTO registrationDto) {
        if (registrationDto.getUsername() == null || registrationDto.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (registrationDto.getFirstName() == null || registrationDto.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (registrationDto.getLastName() == null || registrationDto.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        if (registrationDto.getEmail() == null || !registrationDto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (registrationDto.getPassword() == null || registrationDto.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        if (registrationDto.getPhoneNumber() != null && !registrationDto.getPhoneNumber().matches("^\\+?[0-9. ()-]{7,}$")) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        if (registrationDto.getRole() == null) {
            throw new IllegalArgumentException("Role must be specified");
        }
    }

    private void checkForDuplicateFields(UserRegistrationRequestDTO registrationDto) {
        if (registrationDAO.existsByEmail(registrationDto.getEmail())) {
            throw new UniqueConstraintViolationException("Email is already in use");
        }
        if (registrationDAO.existsByPhoneNumber(registrationDto.getPhoneNumber())) {
            throw new UniqueConstraintViolationException("Phone number is already in use");
        }
        if (registrationDAO.existsByUsername(registrationDto.getUsername())) {
            throw new UniqueConstraintViolationException("Username is already in use");
        }
    }

    private User mapToUser(UserRegistrationRequestDTO registrationDto) {
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(registrationDto.getPassword()); // Password should be encoded
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        user.setRole(registrationDto.getRole());
        return user;
    }

    private UserRegistrationResponseDTO  mapToResponseDto(User user) {
        UserRegistrationResponseDTO responseDto = new UserRegistrationResponseDTO();
        responseDto.setId(user.getId());
        responseDto.setUsername(user.getUsername());
        responseDto.setFirstName(user.getFirstName());
        responseDto.setLastName(user.getLastName());
        responseDto.setEmail(user.getEmail());
        responseDto.setPhoneNumber(user.getPhoneNumber());
        responseDto.setRole(user.getRole());
        return responseDto;
    }
}
