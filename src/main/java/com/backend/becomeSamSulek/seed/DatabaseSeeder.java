package com.backend.becomeSamSulek.seed;

import com.backend.becomeSamSulek.model.Exercise;
import com.backend.becomeSamSulek.repository.ExerciseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    @Autowired
    ExerciseRepository exerciseRepository;

    @Override
    public void run(String... args) throws Exception {
        seedDatabase();
    }

    private void seedDatabase() {

        try {
            System.out.println("Seeding database");
            if(exerciseRepository.findAll().isEmpty()){
                ClassPathResource resource = new ClassPathResource("exercises/exercises.json");
                InputStream inputStream = resource.getInputStream();
                byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
                String jsonContent = new String(bytes, StandardCharsets.UTF_8);

                ObjectMapper objectMapper = new ObjectMapper();
                List<Exercise> exercises = objectMapper.readValue(jsonContent, new TypeReference<List<Exercise>>() {});
                exerciseRepository.saveAll(exercises);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}