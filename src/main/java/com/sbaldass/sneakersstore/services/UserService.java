package com.sbaldass.sneakersstore.services;

import com.sbaldass.sneakersstore.domain.Role;
import com.sbaldass.sneakersstore.domain.RoleName;
import com.sbaldass.sneakersstore.domain.User;
import com.sbaldass.sneakersstore.repository.RoleRepository;
import com.sbaldass.sneakersstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void registerUser(User userDTO) {
        User user = new User();
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role role = roleRepository.findByName(RoleName.CUSTOMER).orElseThrow(() -> new RuntimeException("Role not found."));
        user.setRole(role);

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUsername(userDTO.getUsername());
        user.setPhotoUrl(userDTO.getPhotoUrl());

        userRepository.save(user);
    }

    public void updateUser(User userDTO, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role role = roleRepository.findByName(RoleName.CUSTOMER).orElseThrow(() -> new RuntimeException("Role not found."));
        user.setRole(role);

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUsername(userDTO.getUsername());
        user.setPhotoUrl(userDTO.getPhotoUrl());

        userRepository.save(user);
    }

    public User loginUser(User request) {
            String email = request.getUsername();
            Optional<User> existingUser = userRepository.findByUsername(email);
            if (existingUser.isPresent()) {
                throw new RuntimeException(String.format("User with the email address '%s' already exists.", email));
            }

            String hashedPassword = passwordEncoder.encode(request.getPassword());
            return new User(email, request.getEmail(), hashedPassword);
        }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public User createAdministrator(User input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleName.ADMIN);

        if (optionalRole.isEmpty()) {
            return null;
        }

        var user = new User();
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(optionalRole.get());

        return userRepository.save(user);
    }

    public User createVendor(User input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleName.VENDOR);

        if (optionalRole.isEmpty()) {
            return null;
        }

        var user = new User();
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(optionalRole.get());

        return userRepository.save(user);
    }
}
