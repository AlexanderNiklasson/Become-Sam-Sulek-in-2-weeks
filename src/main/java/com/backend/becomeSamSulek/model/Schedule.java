package com.backend.becomeSamSulek.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "schedules")
@AllArgsConstructor
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int ownerId;

    @Column(length = 1000)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String idsJson;

    @Transient
    private List<Day> week;



    public Schedule() {
        String [] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        this.week = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Day day = new Day();
            day.setName(days[i]);
            this.week.add(day);
        }
    }

    public List<List<Integer>> getIds() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(idsJson, List.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void setIds(List<List<Integer>> ids) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.idsJson = objectMapper.writeValueAsString(ids);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
