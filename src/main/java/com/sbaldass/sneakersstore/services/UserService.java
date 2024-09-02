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
        user.setRoles(List.of(role));

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
        user.setRoles(List.of(role));

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUsername(userDTO.getUsername());
        user.setPhotoUrl(userDTO.getPhotoUrl());

        userRepository.save(user);
    }

    public void loginUser(User userDTO) {
        //TODO
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
