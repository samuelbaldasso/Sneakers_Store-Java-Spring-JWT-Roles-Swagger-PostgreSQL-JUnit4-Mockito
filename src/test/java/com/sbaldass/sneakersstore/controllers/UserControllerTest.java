package com.sbaldass.sneakersstore.controllers;

import com.sbaldass.sneakersstore.domain.User;
import com.sbaldass.sneakersstore.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticatedUser() {
        User user = new User();
        user.setEmail("test@example.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<User> response = userController.authenticatedUser();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testAllUsers() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userService.findAll()).thenReturn(Collections.singletonList(user));

        ResponseEntity<List<User>> response = userController.allUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(user), response.getBody());
        verify(userService, times(1)).findAll();
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        User user = new User();
        user.setEmail("updated@example.com");

        userController.updateUser(user, userId);

        verify(userService, times(1)).updateUser(user, userId);
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        userController.deleteUser(userId);

        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    public void testGetUser() {
        Long userId = 1L;
        User user = new User();
        user.setEmail("test@example.com");

        when(userService.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> response = userController.getUser(userId);

        assertEquals(Optional.of(user), response);
        verify(userService, times(1)).findById(userId);
    }
}
