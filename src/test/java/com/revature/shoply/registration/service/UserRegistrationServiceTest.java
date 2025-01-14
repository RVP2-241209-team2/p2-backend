package com.revature.shoply.registration.service;

import com.revature.shoply.models.User;
import com.revature.shoply.models.enums.UserRole;
import com.revature.shoply.registration.dto.UserRegistrationRequestDTO;
import com.revature.shoply.registration.dto.UserRegistrationResponseDTO;
import com.revature.shoply.registration.repository.UserRegistrationDAO;
import com.revature.shoply.user.exception.UniqueConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserRegistrationServiceTest {

    @Mock
    private UserRegistrationDAO registrationDAO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserRegistrationService userRegistrationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ========================================
    // registerUser
    // ========================================
    @Test
    public void registerUser_ValidRequest_ReturnsResponseDTO() {
        UserRegistrationRequestDTO requestDTO = new UserRegistrationRequestDTO(
                "testuser", "John", "Doe", "testuser@example.com", "password123", "+1234567890", UserRole.CUSTOMER
        );

        User user = new User(UUID.randomUUID(), "testuser", "John", "Doe",  "testuser@example.com", "password123", "1234567890", UserRole.CUSTOMER);

        when(registrationDAO.existsByEmail(requestDTO.getEmail())).thenReturn(false);
        when(registrationDAO.existsByUsername(requestDTO.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(requestDTO.getPassword())).thenReturn("encodedPassword");
        when(registrationDAO.save(any(User.class))).thenReturn(user);

        UserRegistrationResponseDTO responseDTO = userRegistrationService.registerUser(requestDTO);

        assertThat(responseDTO.getUsername()).isEqualTo("testuser");
        assertThat(responseDTO.getEmail()).isEqualTo("testuser@example.com");
        assertThat(responseDTO.getFirstName()).isEqualTo("John");
        assertThat(responseDTO.getRole()).isEqualTo(UserRole.CUSTOMER);

        verify(registrationDAO, times(1)).save(any(User.class));
    }

    @Test
    public void registerUser_DuplicateEmail_ThrowsException() {
        UserRegistrationRequestDTO requestDTO = new UserRegistrationRequestDTO(
                "testuser", "John", "Doe", "testuser@example.com", "password123", "+1234567890", UserRole.CUSTOMER
        );

        when(registrationDAO.existsByEmail(requestDTO.getEmail())).thenReturn(true);

        Exception exception = assertThrows(UniqueConstraintViolationException.class,
                () -> userRegistrationService.registerUser(requestDTO));
        assertThat(exception.getMessage()).isEqualTo("java.lang.IllegalArgumentException: Email is already in use");
    }

    @Test
    public void registerUser_InvalidEmailFormat_ThrowsException() {
        UserRegistrationRequestDTO requestDTO = new UserRegistrationRequestDTO(
                "testuser", "John", "Doe", "invalidemail", "password123", "+1234567890", UserRole.CUSTOMER
        );

        Exception exception = assertThrows(RuntimeException.class,
                () -> userRegistrationService.registerUser(requestDTO));
        assertThat(exception.getMessage()).isEqualTo("java.lang.IllegalArgumentException: Invalid email format");
    }

    // ========================================
    // validateRegistrationRequest
    // ========================================
    @Test
    public void validateRegistrationRequest_InvalidPasswordLength_ThrowsException() {
        UserRegistrationRequestDTO requestDTO = new UserRegistrationRequestDTO(
                "testuser", "John", "Doe", "testuser@example.com", "short", "+1234567890", UserRole.CUSTOMER
        );

        Exception exception = assertThrows(RuntimeException.class,
                () -> userRegistrationService.registerUser(requestDTO));
        assertThat(exception.getMessage()).isEqualTo("java.lang.IllegalArgumentException: Password must be at least 8 characters long");
    }

    @Test
    public void validateRegistrationRequest_NullUsername_ThrowsException() {
        UserRegistrationRequestDTO requestDTO = new UserRegistrationRequestDTO(
                null, "John", "Doe", "testuser@example.com", "password123", "+1234567890", UserRole.CUSTOMER
        );

        Exception exception = assertThrows(RuntimeException.class,
                () -> userRegistrationService.registerUser(requestDTO));
        assertThat(exception.getMessage()).isEqualTo("java.lang.IllegalArgumentException: Username cannot be empty");
    }

    // ========================================
    // checkForDuplicateFields
    // ========================================
    @Test
    public void checkForDuplicateFields_DuplicateUsername_ThrowsException() {
        UserRegistrationRequestDTO requestDTO = new UserRegistrationRequestDTO(
                "testuser", "John", "Doe", "testuser@example.com", "password123", "+1234567890", UserRole.CUSTOMER
        );

        when(registrationDAO.existsByUsername(requestDTO.getUsername())).thenReturn(true);

        Exception exception = assertThrows(UniqueConstraintViolationException.class,
                () -> userRegistrationService.registerUser(requestDTO));
        assertThat(exception.getMessage()).isEqualTo("java.lang.IllegalArgumentException: Username is already in use");
    }

    @Test
    public void checkForDuplicateFields_DuplicatePhoneNumber_ThrowsException() {
        UserRegistrationRequestDTO requestDTO = new UserRegistrationRequestDTO(
                "testuser", "John", "Doe", "testuser@example.com", "password123", "+1234567890", UserRole.CUSTOMER
        );

        when(registrationDAO.existsByPhoneNumber(requestDTO.getPhoneNumber())).thenReturn(true);

        Exception exception = assertThrows(UniqueConstraintViolationException.class,
                () -> userRegistrationService.registerUser(requestDTO));
        assertThat(exception.getMessage()).isEqualTo("java.lang.IllegalArgumentException: Phone number is already in use");
    }

}
