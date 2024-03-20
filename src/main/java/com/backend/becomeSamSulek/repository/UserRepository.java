package com.backend.becomeSamSulek.repository;

import com.backend.becomeSamSulek.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User getOneByUsername(String username);

    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);



}
