package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class HabitDTO {
    private Long id;
    private String habitName;
    private String frequency;
    private LocalDate lastCompletedDate;
    private int currentStreak;

    public HabitDTO(Long id, String habitName, String frequency, LocalDate lastCompletedDate, int currentStreak) {
        this.id = id;
        this.habitName = habitName;
        this.frequency = frequency;
        this.lastCompletedDate = lastCompletedDate;
        this.currentStreak = currentStreak;
    }
}