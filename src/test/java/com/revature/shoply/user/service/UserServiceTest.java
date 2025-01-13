package com.revature.shoply.user.service;

import com.revature.shoply.models.Address;
import com.revature.shoply.models.PaymentDetails;
import com.revature.shoply.models.User;
import com.revature.shoply.models.enums.AddressType;
import com.revature.shoply.models.enums.UserRole;
import com.revature.shoply.user.DTO.*;
import com.revature.shoply.user.repository.AddressDAO;
import com.revature.shoply.user.repository.PaymentMethodDAO;
import com.revature.shoply.repositories.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private AddressDAO addressDAO;

    @Mock
    private PaymentMethodDAO paymentMethodDAO;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ========================================
    // USERS
    // ========================================

    // 1. getAllUsers
    @Test
    public void getAllUsers_Valid_ReturnsUsers() {
        User user = new User(UUID.randomUUID(), "testuser", "Test", "User",  "test@email.com", "password", "1234567890", UserRole.CUSTOMER);
        when(userDAO.findAll()).thenReturn(Collections.singletonList(user));

        List<OutgoingUserDTO> users = userService.getAllUsers();

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getUsername()).isEqualTo("testuser");
    }

    // 2. getUserInfo
    @Test
    public void getUserInfo_ValidUserId_ReturnsUserDTO() {
        UUID userId = UUID.randomUUID();
        User user = new User(UUID.randomUUID(), "testuser", "Test", "User",  "test@email.com", "password", "1234567890", UserRole.CUSTOMER);
        when(userDAO.findById(userId)).thenReturn(Optional.of(user));

        OutgoingUserDTO userInfo = userService.getUserInfo(userId);

        assertThat(userInfo.getUsername()).isEqualTo("testuser");
        assertThat(userInfo.getFirstname()).isEqualTo("Test");
    }

    @Test
    public void getUserInfo_InvalidUserId_ThrowsException() {
        UUID userId = UUID.randomUUID();
        when(userDAO.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.getUserInfo(userId));
        assertThat(exception.getMessage()).isEqualTo("No user found by ID: " + userId);
    }

    // 3. updateUser
    @Test
    public void updateUser_Valid_ReturnsUpdatedUser() {
        UUID userId = UUID.randomUUID();
        User user = new User(UUID.randomUUID(), "testuser", "Test", "User",  "test@email.com", "password", "1234567890", UserRole.CUSTOMER);
        IncomingUserDTO incomingUserDTO = new IncomingUserDTO("newuser", "new@email.com", "Jane", "Smith", "9876543210");
        when(userDAO.findById(userId)).thenReturn(Optional.of(user));
        when(userDAO.findByUsername("newuser")).thenReturn(null);
        when(userDAO.save(any(User.class))).thenReturn(user);

        OutgoingUserDTO updatedUser = userService.updateUser(userId, incomingUserDTO);

        assertThat(updatedUser.getUsername()).isEqualTo("newuser");
        assertThat(updatedUser.getEmail()).isEqualTo("new@email.com");
    }

    @Test
    public void updateUser_DuplicateUsername_ThrowsException() {
        UUID userId = UUID.randomUUID();
        User user = new User(UUID.randomUUID(), "testuser", "Test", "User",  "test@email.com", "password", "1234567890", UserRole.CUSTOMER);
        IncomingUserDTO incomingUserDTO = new IncomingUserDTO("duplicateuser", "new@email.com", "Jane", "Smith", "9876543210");
        when(userDAO.findById(userId)).thenReturn(Optional.of(user));
        when(userDAO.findByUsername("duplicateuser")).thenReturn(new User());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.updateUser(userId, incomingUserDTO));
        assertThat(exception.getMessage()).contains("Username duplicateuser is already taken");
    }

    // 4. deleteUser
    @Test
    public void deleteUser_ValidUserId_ReturnsTrue() {
        UUID userId = UUID.randomUUID();
        User user = new User(UUID.randomUUID(), "testuser", "Test", "User",  "test@email.com", "password", "1234567890", UserRole.CUSTOMER);
        when(userDAO.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userDAO).deleteById(userId);

        boolean result = userService.deleteUser(userId);

        assertThat(result).isTrue();
    }

    // ========================================
    // ADDRESSES
    // ========================================

    // 5. addAddress
    @Test
    public void addAddress_ValidData_ReturnsAddress() {
        UUID userId = UUID.randomUUID();
        User user = new User(UUID.randomUUID(), "testuser", "Test", "User",  "test@email.com", "password", "1234567890", UserRole.CUSTOMER);
        IncomingAddressDTO incomingAddress = new IncomingAddressDTO("123 Main St", "Apt 1", "City", "State", "12345", "USA", AddressType.BILLING);
        Address address = new Address(UUID.randomUUID(), user, "123 Main St", "Apt 1", "City", "State", "12345", "USA", AddressType.BILLING);

        when(userDAO.findById(userId)).thenReturn(Optional.of(user));
        when(addressDAO.save(any(Address.class))).thenReturn(address);

        Address savedAddress = userService.addAddress(userId, incomingAddress);

        assertThat(savedAddress.getAddressLine1()).isEqualTo("123 Main St");
    }

    @Test
    public void addAddress_InvalidUserId_ThrowsException() {
        UUID userId = UUID.randomUUID();
        IncomingAddressDTO incomingAddress = new IncomingAddressDTO("123 Main St", "Apt 1", "City", "State", "12345", "USA", AddressType.BILLING);

        when(userDAO.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.addAddress(userId, incomingAddress));
        assertThat(exception.getMessage()).contains("No user found by ID");
    }

    // ========================================
    // PAYMENT METHODS
    // ========================================

    // 6. getPaymentMethods
    @Test
    public void getPaymentMethods_Valid_ReturnsPaymentMethods() {
        UUID userId = UUID.randomUUID();
        User user = new User(UUID.randomUUID(), "testuser", "Test", "User",  "test@email.com", "password", "1234567890", UserRole.CUSTOMER);
        PaymentDetails payment = new PaymentDetails(UUID.randomUUID(), user, "1234-5678-9876-5432", "John Doe", "12/25", null, true);

        when(userDAO.findById(userId)).thenReturn(Optional.of(user));
        when(paymentMethodDAO.findByUserId(userId)).thenReturn(Collections.singletonList(payment));

        List<PaymentDetails> paymentMethods = userService.getPaymentMethods(userId);

        assertThat(paymentMethods).hasSize(1);
        assertThat(paymentMethods.get(0).getCardHolderName()).isEqualTo("John Doe");
    }

    @Test
    public void getPaymentMethods_InvalidUserId_ThrowsException() {
        UUID userId = UUID.randomUUID();
        when(userDAO.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.getPaymentMethods(userId));
        assertThat(exception.getMessage()).contains("No user found by ID");
    }
}
