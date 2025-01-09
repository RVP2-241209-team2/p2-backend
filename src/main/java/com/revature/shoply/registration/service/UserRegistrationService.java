package com.revature.shoply.registration.service;

import com.revature.shoply.models.User;
import com.revature.shoply.registration.dto.UserRegistrationRequestDTO;
import com.revature.shoply.registration.dto.UserRegistrationResponseDTO;
import com.revature.shoply.registration.repository.UserRegistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationService.class);

    private final UserRegistrationRepository registrationRepository;

    public UserRegistrationService(UserRegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public UserRegistrationResponseDTO registerUser(UserRegistrationRequestDTO registrationRequestDTO) {
        log.info("Registering user: {}", registrationRequestDTO);
        try {
            validateRegistrationRequest(registrationRequestDTO);
            checkForDuplicateFields(registrationRequestDTO);
            User user = mapToUser(registrationRequestDTO);
            User savedUser = registrationRepository.save(user);
            return mapToResponseDto(savedUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void validateRegistrationRequest(UserRegistrationRequestDTO registrationDto) {
        log.debug("Validating registration request: {}", registrationDto);
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
        log.debug("Checking for duplicate fields: {}", registrationDto);
        if (registrationRepository.existsByEmail(registrationDto.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }
        if (registrationRepository.existsByPhoneNumber(registrationDto.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number is already in use");
        }
        if (registrationRepository.existsByUsername(registrationDto.getUsername())) {
            throw new IllegalArgumentException("Username is already in use");
        }
    }

    private User mapToUser(UserRegistrationRequestDTO registrationDto) {
        log.debug("Mapping registration request to User: {}", registrationDto);
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
        log.debug("Mapping User to response DTO: {}", user);
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
