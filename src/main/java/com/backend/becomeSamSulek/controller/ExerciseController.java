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
    private ResponseEntity<Iterable<Exercise>> getAll(@RequestParam("level") String level){
        if(level == null)
            return new ResponseEntity<>(exerciseService.getAllExercises(), HttpStatus.OK);
        else if(level.equals("beginner") || level.equals("intermediate") || level.equals("expert")){

            return new ResponseEntity<>(exerciseService.getAllExercises(level), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    private ResponseEntity<Exercise> getOne(@PathVariable int id){
        return new ResponseEntity<>(exerciseService.getOneExercise(id), HttpStatus.OK);
    }
}
