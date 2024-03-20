package com.backend.becomeSamSulek.helper;

import com.backend.becomeSamSulek.model.Exercise;
import com.backend.becomeSamSulek.model.Schedule;
import com.backend.becomeSamSulek.repository.ExerciseRepository;
import com.backend.becomeSamSulek.requests.WorkoutGenerateRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorkoutGenerator {

    private static final int DAYS_IN_WEEK = 7;
    private static int EXERCISES_PER_DAY = 4;

    @Autowired
    private static ExerciseRepository exerciseRepository;

    private static List<Exercise> allExercises;

    public static Schedule generateWorkout(WorkoutGenerateRequest workoutGenerateRequest, List<Exercise> all) {
        allExercises = all;
        String level = workoutGenerateRequest.getLevel();

        System.out.println("LEVEL:" + level);

        if(level.equals("beginner")){
            EXERCISES_PER_DAY = 4;
        }else if(level.equals("intermediate")){
            EXERCISES_PER_DAY = 6;
        }else if(level.equals("expert")){
            EXERCISES_PER_DAY = 8;
        }
        System.out.println(workoutGenerateRequest.getType());

        return switch (workoutGenerateRequest.getType()) {
            case "ppl" -> generatePpl(workoutGenerateRequest);
            case "arnold" -> generateArnold(workoutGenerateRequest);
            default -> null;
        };
    }

    private static Schedule generatePpl(WorkoutGenerateRequest workoutGenerateRequest) {
        Schedule schedule = new Schedule();
        schedule.setName(workoutGenerateRequest.getName());
        schedule.setOwnerId(workoutGenerateRequest.getOwnerId());

        Random random = new Random();
        List<List<Integer>> ids = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            List<Integer> dayExerciseIds = new ArrayList<>();
            List<Exercise> categoryExercises;

            if (i == 0 || i == 3) {
                categoryExercises = filterExercisesByPrimaryMuscles(allExercises, "chest", "triceps", "shoulders");
            } else if (i == 1 || i == 4) {
                categoryExercises = filterExercisesByPrimaryMuscles(allExercises, "back", "biceps", "rear deltoids");
            } else if (i == 6) {
                categoryExercises = filterExercisesByPrimaryMuscles(allExercises, "abs", "obliques");
            } else {
                categoryExercises = filterExercisesByPrimaryMuscles(allExercises, "quadriceps", "hamstrings", "calves");
            }

            if(i == 3){
                dayExerciseIds = ids.get(0);
            }
            else if(i == 4) {
                dayExerciseIds = ids.get(1);
            }
            else if(i == 5){
                dayExerciseIds = ids.get(2);
            }
            else{
                for (int j = 0; j < EXERCISES_PER_DAY; j++) {
                    if(categoryExercises.isEmpty()){
                        break;
                    }
                    int randomIndex = random.nextInt(categoryExercises.size());
                    Exercise randomExercise = categoryExercises.get(randomIndex);
                    dayExerciseIds.add(randomExercise.getIntId());
                    // Remove the selected exercise to prevent duplicates
                    categoryExercises.remove(randomIndex);
                }
            }


            ids.add(dayExerciseIds);
        }

        schedule.setIds(ids);
        System.out.println(schedule.getIds());
        System.out.println("TEST");
        return schedule;
    }


        private static Schedule generateArnold(WorkoutGenerateRequest workoutGenerateRequest) {
            Schedule schedule = new Schedule();
            schedule.setName(workoutGenerateRequest.getName());
            schedule.setOwnerId(workoutGenerateRequest.getOwnerId());

            Random random = new Random();
            List<List<Integer>> ids = new ArrayList<>();

            for (int i = 0; i < 7; i++) {
                List<Integer> dayExerciseIds = new ArrayList<>();
                List<Exercise> categoryExercises;

                if (i == 0 || i == 3) {

                    categoryExercises = filterExercisesByPrimaryMuscles(allExercises, "chest", "back");
                } else if (i == 1 || i == 4) {

                    categoryExercises = filterExercisesByPrimaryMuscles(allExercises, "shoulders", "biceps", "triceps");
                } else if (i == 6) {

                    categoryExercises = filterExercisesByPrimaryMuscles(allExercises, "abs", "obliques");

                } else {

                    categoryExercises = filterExercisesByPrimaryMuscles(allExercises, "quadriceps", "hamstrings", "calves");
                }

                if (i == 3) {
                    dayExerciseIds = ids.get(0);
                } else if (i == 4) {
                    dayExerciseIds = ids.get(1);
                } else if (i == 5) {
                    dayExerciseIds = ids.get(2);
                } else {
                    for (int j = 0; j < EXERCISES_PER_DAY; j++) {
                        if (categoryExercises.isEmpty()) {
                            break;
                        }
                        int randomIndex = random.nextInt(categoryExercises.size());
                        Exercise randomExercise = categoryExercises.get(randomIndex);
                        dayExerciseIds.add(randomExercise.getIntId());
                        // Remove the selected exercise to prevent duplicates
                        categoryExercises.remove(randomIndex);
                    }
                }

                ids.add(dayExerciseIds);
            }

            schedule.setIds(ids);
            return schedule;
        }


    private static List<Exercise> filterExercisesByPrimaryMuscles(List<Exercise> exercises, String... primaryMuscles) {
        List<Exercise> filteredExercises = new ArrayList<>();
        for (Exercise exercise : exercises) {
            for (String muscle : primaryMuscles) {
                if (exercise.getPrimaryMuscles().contains(muscle)) {
                    filteredExercises.add(exercise);
                    break;
                }
            }
        }
        return filteredExercises;
    }
}
