package com.backend.becomeSamSulek.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exercises")
public class Exercise {

    private String id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int intId;

    private String name;
    private String force;
    private String level;
    private String mechanic;
    private String equipment;
    @Column(length = 1000)
    private List<String> primaryMuscles;
    @Column(length = 1000)
    private List<String> secondaryMuscles;
    @Column(length = 1000)
    private List<String> instructions;
    @Column(length = 1000)
    private String category;
    @Column(length = 1000)
    private List<String> images;
}
