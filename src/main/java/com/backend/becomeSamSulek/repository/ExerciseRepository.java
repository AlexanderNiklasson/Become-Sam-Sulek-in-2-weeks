package com.backend.becomeSamSulek.repository;

import com.backend.becomeSamSulek.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    public Optional<Exercise> findByIntId(int id);

    public List<Exercise> findAllByLevel(String level);

}
