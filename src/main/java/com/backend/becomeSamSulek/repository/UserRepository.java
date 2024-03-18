package com.backend.becomeSamSulek.repository;

import com.backend.becomeSamSulek.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User getOneByUsername(String username);

}
