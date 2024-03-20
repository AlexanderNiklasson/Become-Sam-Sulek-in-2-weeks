package com.backend.becomeSamSulek.seed;

import com.backend.becomeSamSulek.controller.AuthController;
import com.backend.becomeSamSulek.model.ERole;
import com.backend.becomeSamSulek.model.Exercise;
import com.backend.becomeSamSulek.model.Role;
import com.backend.becomeSamSulek.model.User;
import com.backend.becomeSamSulek.repository.ExerciseRepository;
import com.backend.becomeSamSulek.repository.RoleRepository;
import com.backend.becomeSamSulek.repository.UserRepository;
import com.backend.becomeSamSulek.requests.SignupRequest;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthController authController;

    @Autowired
    RoleRepository roleRepository;

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

                ComplexityAlgorithm.calculateComplexity(exercises);
                exerciseRepository.saveAll(exercises);



                List<Role> roles = new ArrayList<>();
                roles.add(new Role(ERole.ROLE_USER));
                roles.add(new Role(ERole.ROLE_MODERATOR));
                roles.add(new Role(ERole.ROLE_ADMIN));
                roleRepository.saveAll(roles);

                SignupRequest user1 = new SignupRequest();
                user1.setUsername("user");
                user1.setEmail("user@user.com");
                user1.setPassword("password");
                Set<String> roles1 = new HashSet<>();
                roles1.add("user");
                user1.setRole(roles1);
                authController.registerUser(user1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
