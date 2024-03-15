package com.backend.becomeSamSulek.controller;

import com.backend.becomeSamSulek.model.Exercise;
import com.backend.becomeSamSulek.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
    @Autowired
    ExerciseService exerciseService;

    @GetMapping //
    private ResponseEntity<Iterable<Exercise>> getAll(@RequestParam(value = "level", required=false) String level) {
        return new ResponseEntity<>(exerciseService.getAllExercises(level), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Exercise> getOne(@PathVariable int id){
        return new ResponseEntity<>(exerciseService.getOneExercise(id), HttpStatus.OK);
    }
}
