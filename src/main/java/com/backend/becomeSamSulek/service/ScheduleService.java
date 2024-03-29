package com.backend.becomeSamSulek.service;

import com.backend.becomeSamSulek.helper.WorkoutGenerator;
import com.backend.becomeSamSulek.model.Day;
import com.backend.becomeSamSulek.model.Exercise;
import com.backend.becomeSamSulek.model.Schedule;
import com.backend.becomeSamSulek.repository.ExerciseRepository;
import com.backend.becomeSamSulek.repository.ScheduleRepository;
import com.backend.becomeSamSulek.requests.WorkoutGenerateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    public Iterable<Schedule> getAll() {
        return scheduleRepository.findAll();
    }
    public Schedule getOne(int id) {
        Schedule schedule = scheduleRepository.findByOwnerId(id).orElse(null);

        if(schedule != null){
            List<Day> days = schedule.getWeek();
            int index = 0;
            for(Day day : days){
                day.setExercises(new ArrayList<>());
                List<Exercise> exercises = day.getExercises();
               for(Integer i : schedule.getIds().get(index)){
                   exercises.add(exerciseRepository.findByIntId(i).orElseThrow(() -> new HttpStatusCodeException(HttpStatus.NOT_FOUND) {
                   }));
               }
                index++;

            }
        }

        return schedule;
    }

    public Schedule addSchedule(Schedule schedule) {
        Schedule scheduleTemp = scheduleRepository
                .findByName(schedule.getName())
                .orElse(null );
        if(scheduleTemp != null){
            throw new HttpStatusCodeException(HttpStatus.BAD_REQUEST, "Schedule already exists") {};
        }
        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(int id, Schedule schedule) {
        Schedule scheduleTemp = scheduleRepository
                .findByOwnerId(id)
                .orElseThrow(() -> new HttpStatusCodeException(HttpStatus.NOT_FOUND) {
                });
        scheduleTemp.setWeek(schedule.getWeek());
        scheduleTemp.setIds(schedule.getIds());
        return scheduleRepository.save(scheduleTemp);
    }

    public void deleteSchedule(int id) {
        Schedule schedule = scheduleRepository
                .findByOwnerId(id)
                .orElseThrow(() -> new HttpStatusCodeException(HttpStatus.NOT_FOUND) {
                });
        scheduleRepository.delete(schedule);
    }

    public Schedule generateSchedule(WorkoutGenerateRequest workoutGenerateRequest) {

        return scheduleRepository.save(WorkoutGenerator.generateWorkout(workoutGenerateRequest, exerciseRepository.findAll()));

    }
}
