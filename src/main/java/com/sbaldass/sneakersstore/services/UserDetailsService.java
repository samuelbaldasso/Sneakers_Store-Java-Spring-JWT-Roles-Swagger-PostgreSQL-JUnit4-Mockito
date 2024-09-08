package com.sbaldass.sneakersstore.services;

import com.sbaldass.sneakersstore.domain.Role;
import com.sbaldass.sneakersstore.domain.User;
import com.sbaldass.sneakersstore.dto.LoginUserDTO;
import com.sbaldass.sneakersstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found."));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user.getRole())
        );
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName());
        return List.of(authority);
    }
}
