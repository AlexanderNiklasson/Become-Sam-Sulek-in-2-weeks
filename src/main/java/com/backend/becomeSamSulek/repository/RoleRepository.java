package com.backend.becomeSamSulek.repository;



import com.backend.becomeSamSulek.model.ERole;
import com.backend.becomeSamSulek.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
