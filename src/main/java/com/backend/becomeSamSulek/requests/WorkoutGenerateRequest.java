package com.backend.becomeSamSulek.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkoutGenerateRequest {
    private String name;
    private String level;
    private int duration;
    private String type;
    private List<String> excludedMuscleGroups;
    private int ownerId;
}
