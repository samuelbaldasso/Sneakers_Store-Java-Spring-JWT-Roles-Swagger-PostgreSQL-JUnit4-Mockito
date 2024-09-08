package com.sbaldass.sneakersstore.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sbaldass.sneakersstore.domain.Role;
import com.sbaldass.sneakersstore.domain.RoleName;
import com.sbaldass.sneakersstore.domain.User;
import com.sbaldass.sneakersstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @InjectMocks
    private UserDetailsService userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    @Test
    public void testLoadUserByUsername_UserExists() {
        String email = "test@example.com";
        String password = "encodedPassword";
        Role role = new Role();
        role.setName(RoleName.ADMIN);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(user.getPassword()).thenReturn(password);
        when(user.getRole()).thenReturn(role);
        when(user.getEmail()).thenReturn(email);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userDetailsService.loadUserByUsername(email),
                "Expected loadUserByUsername to throw UsernameNotFoundException"
        );

        assertTrue(exception.getMessage().contains("User not found."));
        verify(userRepository, times(1)).findByEmail(email);
    }
}
