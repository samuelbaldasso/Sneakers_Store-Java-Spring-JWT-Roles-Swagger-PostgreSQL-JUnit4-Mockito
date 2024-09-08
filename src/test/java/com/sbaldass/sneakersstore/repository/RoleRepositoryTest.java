package com.sbaldass.sneakersstore.repository;

import com.sbaldass.sneakersstore.domain.Role;
import com.sbaldass.sneakersstore.domain.RoleName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleRepositoryTest {

    @Mock
    private RoleRepository roleRepository;

    @Test
    public void testFindByNameCustomer() {
        String name = "CUSTOMER";
        Role role = new Role();
        Optional<Role> roleOptional = Optional.of(role);
        role.setName(RoleName.valueOf(name));
        when(roleRepository.findByName(RoleName.valueOf(name))).thenReturn(roleOptional);
        Optional<Role> result = roleRepository.findByName(RoleName.valueOf(name));
        assertEquals(roleOptional, result);
        verify(roleRepository, times(1)).findByName(RoleName.valueOf(name));
    }

    @Test
    public void testFindByNameAdmin() {
        String name = "ADMIN";
        Role role = new Role();
        Optional<Role> roleOptional = Optional.of(role);
        role.setName(RoleName.valueOf(name));
        when(roleRepository.findByName(RoleName.valueOf(name))).thenReturn(roleOptional);
        Optional<Role> result = roleRepository.findByName(RoleName.valueOf(name));
        assertEquals(roleOptional, result);
        verify(roleRepository, times(1)).findByName(RoleName.valueOf(name));
    }

}
