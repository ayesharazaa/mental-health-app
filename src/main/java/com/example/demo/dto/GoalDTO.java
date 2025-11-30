package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class GoalDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate targetDate;
    private String status;
        public GoalDTO(Long id, String title, String description, LocalDate targetDate, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.targetDate = targetDate;
        this.status = status;
    }
}