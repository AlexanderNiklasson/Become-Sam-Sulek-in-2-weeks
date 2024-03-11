package com.backend.becomeSamSulek.controller;

import com.backend.becomeSamSulek.model.Exercise;
import com.backend.becomeSamSulek.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    @Autowired
    ExerciseService exerciseService;
    @GetMapping("/{id}")
    private ResponseEntity<Exercise> getOne(@PathVariable int id){
        return new ResponseEntity<>(exerciseService.getOneExercise(id), HttpStatus.OK);
    }
}
