package com.backend.becomeSamSulek.repository;

import com.backend.becomeSamSulek.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    public Optional<Schedule> findByName(String name);

    public Optional<Schedule> findByOwnerId(int ownerId);

}
