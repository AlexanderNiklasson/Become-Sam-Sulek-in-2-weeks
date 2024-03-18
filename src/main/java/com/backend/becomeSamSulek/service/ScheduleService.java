package com.backend.becomeSamSulek.service;

import com.backend.becomeSamSulek.model.Day;
import com.backend.becomeSamSulek.model.Exercise;
import com.backend.becomeSamSulek.model.Schedule;
import com.backend.becomeSamSulek.repository.ExerciseRepository;
import com.backend.becomeSamSulek.repository.ScheduleRepository;
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
}
