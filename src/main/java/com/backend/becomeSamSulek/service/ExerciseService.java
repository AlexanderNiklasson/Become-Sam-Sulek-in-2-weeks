package com.backend.becomeSamSulek.service;

import com.backend.becomeSamSulek.model.Exercise;
import com.backend.becomeSamSulek.repository.ExerciseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<Exercise> getAllExercises(String level){
        if(level == null)
            return exerciseRepository.findAll();
        else {
            return exerciseRepository.findAllByLevel(level);
        }
    }
    public Exercise getOneExercise(int id){
        return exerciseRepository
                .findByIntId(id)
                .orElseThrow(() -> new HttpStatusCodeException(HttpStatus.NOT_FOUND) {
        });
    }

}