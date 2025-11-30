package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
public class AnalyticsDTO {
    private Double averageMoodIntensity;
    private Double averageStressLevel;
    private Map<String, Long> moodCounts;
    private Map<String, Long> emotionCounts;
    private int totalJournals;
    private int totalMoods;
    private int completedGoals;
    private int pendingGoals;
}