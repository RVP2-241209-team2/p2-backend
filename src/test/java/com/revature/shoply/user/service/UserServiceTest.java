//package com.revature.shoply.user.service;
//
//import com.revature.shoply.models.Address;
//import com.revature.shoply.models.PaymentDetails;
//import com.revature.shoply.models.User;
//import com.revature.shoply.user.DTO.*;
//import com.revature.shoply.user.repository.AddressDAO;
//import com.revature.shoply.user.repository.PaymentMethodDAO;
//import com.revature.shoply.repositories.UserDAO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class UserServiceTest {
//
//    @Mock
//    private UserDAO userDAO;
//
//    @Mock
//    private AddressDAO addressDAO;
//
//    @Mock
//    private PaymentMethodDAO paymentMethodDAO;
//
//    @InjectMocks
//    private UserService userService;
//
//    private UUID userId;
//    private UUID addressId;
//    private UUID payMethodId;
//
//    private User user;
//    private Address address;
//    private PaymentDetails paymentDetails;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        userId = UUID.randomUUID();
//        addressId = UUID.randomUUID();
//        payMethodId = UUID.randomUUID();
//
//        user = new User(userId, "testUser", "test@example.com", "Test", "User", "1234567890", "password", "STORE_OWNER");
//        address = new Address(addressId, user, "123 Main St", "Apt 4B", "New York", "NY", "10001", "USA", "BILLING");
//        paymentDetails = new PaymentDetails(payMethodId, user, "4111111111111111", "Test User", "12/25", address, true);
//    }
//
//    @Test
//    void findUserByIdAndValidate_UserExists() {
//        when(userDAO.findById(userId)).thenReturn(Optional.of(user));
//
//        User result = userService.findUserByIdAndValidate(userId);
//
//        assertNotNull(result);
//        assertEquals(userId, result.getId());
//        verify(userDAO, times(1)).findById(userId);
//    }
//
//    @Test
//    void findUserByIdAndValidate_UserDoesNotExist() {
//        when(userDAO.findById(userId)).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            userService.findUserByIdAndValidate(userId);
//        });
//
//        assertEquals("No user found by ID: " + userId, exception.getMessage());
//    }
//
//    @Test
//    void findAddressByIdAndValidate_AddressExists() {
//        when(addressDAO.findById(addressId)).thenReturn(Optional.of(address));
//
//        Address result = userService.findAddressByIdAndValidate(addressId);
//
//        assertNotNull(result);
//        assertEquals(addressId, result.getId());
//        verify(addressDAO, times(1)).findById(addressId);
//    }
//
//    @Test
//    void findAddressByIdAndValidate_AddressDoesNotExist() {
//        when(addressDAO.findById(addressId)).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            userService.findAddressByIdAndValidate(addressId);
//        });
//
//        assertEquals("No Address found with AddressID: " + addressId, exception.getMessage());
//    }
//
//    @Test
//    void getUserInfo_Success() {
//        when(userDAO.findById(userId)).thenReturn(Optional.of(user));
//
//        OutgoingUserDTO result = userService.getUserInfo(userId);
//
//        assertNotNull(result);
//        assertEquals(userId, result.getId());
//        verify(userDAO, times(1)).findById(userId);
//    }
//
//    @Test
//    void updateUser_Success() {
//        IncomingUserDTO incomingUser = new IncomingUserDTO("newUser", "new@example.com", "New", "User", "9876543210");
//
//        when(userDAO.findById(userId)).thenReturn(Optional.of(user));
//        when(userDAO.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        OutgoingUserDTO result = userService.updateUser(userId, incomingUser);
//
//        assertNotNull(result);
//        assertEquals("newUser", result.getUsername());
//        verify(userDAO, times(1)).save(any(User.class));
//    }
//
//    @Test
//    void deleteUser_UserExists() {
//        when(userDAO.findById(userId)).thenReturn(Optional.of(user));
//
//        boolean result = userService.deleteUser(userId);
//
//        assertTrue(result);
//        verify(userDAO, times(1)).deleteById(userId);
//    }
//
//    @Test
//    void deleteUser_UserDoesNotExist() {
//        when(userDAO.findById(userId)).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            userService.deleteUser(userId);
//        });
//
//        assertEquals("No user found by ID: " + userId, exception.getMessage());
//    }
//
//    @Test
//    void updateUserPassword_Success() {
//        String oldPassword = "password";
//        String newPassword = "newPassword";
//
//        when(userDAO.findById(userId)).thenReturn(Optional.of(user));
//        when(userDAO.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        boolean result = userService.updateUserPassword(userId, oldPassword, newPassword);
//
//        assertTrue(result);
//        assertEquals(newPassword, user.getPassword());
//        verify(userDAO, times(1)).save(user);
//    }
//
//    @Test
//    void updateUserPassword_WrongOldPassword() {
//        String oldPassword = "wrongPassword";
//        String newPassword = "newPassword";
//
//        when(userDAO.findById(userId)).thenReturn(Optional.of(user));
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            userService.updateUserPassword(userId, oldPassword, newPassword);
//        });
//
//        assertEquals("Old password is wrong!", exception.getMessage());
//    }
//}
