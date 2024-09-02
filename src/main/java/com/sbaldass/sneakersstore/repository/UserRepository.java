package com.sbaldass.sneakersstore.repository;

import com.sbaldass.sneakersstore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
