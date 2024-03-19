package com.backend.becomeSamSulek.seed;

import com.backend.becomeSamSulek.model.Exercise;

import java.text.DecimalFormat;
import java.util.List;

import java.util.HashSet;

public class ComplexityAlgorithm {

    static void calculateComplexity(List<Exercise> exercises) {
        HashSet<String> forces = new HashSet<>();
        HashSet<String> levels = new HashSet<>();
        HashSet<String> mechanics = new HashSet<>();
        HashSet<String> equipments = new HashSet<>();
        HashSet<String> primaryMuscles = new HashSet<>();
        HashSet<Integer> instructionCounts = new HashSet<>();

        for(Exercise exercise: exercises) {
            forces.add(exercise.getForce());
            levels.add(exercise.getLevel());
            mechanics.add(exercise.getMechanic());
            equipments.add(exercise.getEquipment());
            primaryMuscles.addAll(exercise.getPrimaryMuscles());
            instructionCounts.add(exercise.getInstructions().size());
        }

        for(Exercise exerciseTEST: exercises) {
            exerciseTEST.setComplexity(calculateComplexitySingleExercise(exerciseTEST));
        }

        System.out.println("Unique forces: " + forces);
        System.out.println("Unique levels: " + levels);
        System.out.println("Unique mechanics: " + mechanics);
        System.out.println("Unique equipments: " + equipments);
        System.out.println("Unique primary muscles: " + primaryMuscles);
        System.out.println("Unique instruction counts: " + instructionCounts);
    }

    static String calculateComplexitySingleExercise(Exercise exercise) {
        double complexity = 0.0;

        // Weight assignment based on properties
        double levelWeight = getLevelWeight(exercise.getLevel());
        double mechanicWeight = getMechanicWeight(exercise.getMechanic());
        double equipmentWeight = getEquipmentWeight(exercise.getEquipment());
        double primaryMusclesWeight = getPrimaryMusclesWeight(exercise.getPrimaryMuscles());
        double forceWeight = getForceWeight(exercise.getForce());
        double instructionCountWeight = exercise.getInstructions().size();

        // Apply weights and calculate complexity
        complexity += levelWeight + mechanicWeight + equipmentWeight +
                primaryMusclesWeight + forceWeight + instructionCountWeight;

        // Normalize the complexity score to fit within the range of 1 to 10, like Kattis!
        complexity = normalizeComplexity(complexity);

        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(complexity);
    }


    static double getEquipmentWeight(String equipment) {
        if (equipment != null) {
            return switch (equipment) {
                case "barbell" -> 5.0;
                case "kettlebells" -> 3.8;
                case "dumbbell" -> 1.5;
                case "exercise ball", "medicine ball", "cable" -> 1.4;
                case "machine", "bands", "e-z curl bar", "foam roll" -> 1.2;
                case "body only", "other" -> 1.0;
                default -> 0.0;
            };
        } else {
            return 0.0;
        }
    }


    static double getPrimaryMusclesWeight(List<String> primaryMuscles) {
        if (primaryMuscles != null && !primaryMuscles.isEmpty()) {
            return switch (primaryMuscles.size()) {
                case 1 -> 1.0;
                case 2 -> 2.5;
                case 3 -> 5.0;
                default -> 0.0;
            };
        } else {
            return 0.0;
        }
    }

    static double getForceWeight(String force) {
        if (force != null) {
            return switch (force) {
                case "pull", "push" -> 3.5;
                case "static" -> 1.5;
                default -> 1.0;
            };
        } else {
            return 0.0;
        }
    }

    static double getLevelWeight(String level) {
        if (level != null) {
            return switch (level) {
                case "expert" -> 5.0;
                case "intermediate" -> 2.5;
                case "beginner" -> 1.0;
                default -> 0.0;
            };
        } else {
            return 0.0;
        }
    }

    static double getMechanicWeight(String mechanic) {
        if (mechanic != null) {
            return switch (mechanic) {
                case "compound" -> 5.0;
                case "isolation" -> 2.0;
                default -> 0.0;
            };
        } else {
            return 0.0;
        }
    }


    static double normalizeComplexity(double complexity) {
        double minComplexity = 1.0;
        double maxComplexity = 10.0;
        return minComplexity + ((complexity - 1.0) / (10.0 - 1.0)) * (maxComplexity - minComplexity);
    }

}

