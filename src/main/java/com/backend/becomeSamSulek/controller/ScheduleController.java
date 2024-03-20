package com.backend.becomeSamSulek.controller;

import com.backend.becomeSamSulek.model.Schedule;
import com.backend.becomeSamSulek.requests.WorkoutGenerateRequest;
import com.backend.becomeSamSulek.service.ScheduleService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
@CrossOrigin
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;


    @GetMapping
    private ResponseEntity<Iterable<Schedule>> getAll() {
        return new ResponseEntity<>(scheduleService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    private ResponseEntity<Schedule> getSchedule(@PathVariable int id) {
        return new ResponseEntity<>(scheduleService.getOne(id), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    private ResponseEntity<Schedule> updateSchedule(@PathVariable int id, @RequestBody Schedule schedule) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id, schedule), HttpStatus.OK);
    }


    @PostMapping
    private ResponseEntity<Schedule> addSchedule(@RequestBody Schedule schedule) {
        return new ResponseEntity<>(scheduleService.addSchedule(schedule), HttpStatus.OK);
    }
    @PostMapping("/generate")
    private ResponseEntity<Schedule> generateSchedule(@RequestBody WorkoutGenerateRequest workoutGenerateRequest) {
        return new ResponseEntity<>(scheduleService.generateSchedule(workoutGenerateRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteSchedule(@PathVariable int id) {
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
