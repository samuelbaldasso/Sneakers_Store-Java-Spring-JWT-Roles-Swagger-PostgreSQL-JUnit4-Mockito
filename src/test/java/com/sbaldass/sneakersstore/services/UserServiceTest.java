package com.sbaldass.sneakersstore.services;

import com.sbaldass.sneakersstore.domain.Role;
import com.sbaldass.sneakersstore.domain.RoleName;
import com.sbaldass.sneakersstore.domain.User;
import com.sbaldass.sneakersstore.repository.RoleRepository;
import com.sbaldass.sneakersstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void testSaveUserWithRoleFound() {
        // Setup User DTO
        User userDto = new User();
        userDto.setName("testUser");
        userDto.setEmail("test@example.com");
        userDto.setPassword("plainPassword");

        // Setup Role
        Role role = new Role();
        role.setName(RoleName.CUSTOMER);

        // Mock Role Repository to return a role
        when(roleRepository.findByName(RoleName.CUSTOMER)).thenReturn(Optional.of(role));

        // Mock Password Encoder to return the encoded password
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

        // Setup expected User entity
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword("encodedPassword"); // Expected encoded password
        user.setRole(role);

        // Mock User Repository to return the saved user
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the service method
        User result = userService.registerUser(userDto);

        // Assertions
        assertNotNull(result, "Result should not be null");
        assertEquals("test@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(role, result.getRole(), "Role should match the expected role");

        // Verifications
        verify(roleRepository, times(1)).findByName(RoleName.CUSTOMER);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testAlterUser() {
        Long id = 1L;
        User userDto = new User();
        userDto.setName("testUser");
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");

        Role role = new Role();
        role.setName(RoleName.ADMIN);
        when(roleRepository.findByName(RoleName.CUSTOMER)).thenReturn(Optional.of(role));

        User existingUser = new User();
        existingUser.setId(id);
        existingUser.setName("existingUser");
        existingUser.setEmail("existing@example.com");
        existingUser.setPassword(passwordEncoder.encode("existingPassword"));
        existingUser.setRole(role);

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));

        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("newEncodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updateUser(userDto, id);

        assertNotNull(result);
        assertEquals("testUser", result.getName());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("newEncodedPassword", result.getPassword());
        assertEquals(role, result.getRole());
        verify(roleRepository, times(1)).findByName(RoleName.CUSTOMER);
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindUserById() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        user.setName("testUser");
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    public void testFindAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteUser() throws Exception {
        Long id = 1L;

        userService.deleteUser(id);

        verify(userRepository, times(1)).deleteById(id);
    }
}