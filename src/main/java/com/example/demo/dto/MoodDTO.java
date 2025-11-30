package com.example.demo.dto;

import com.example.demo.model.Mood;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class MoodDTO {
    private Long id;
    private String moodLevel;
    private int intensity;
    private LocalDateTime date;
    public MoodDTO(Long id, String moodLevel, int intensity, LocalDateTime date) {
        this.id = id;
        this.moodLevel = moodLevel;
        this.intensity = intensity;
        this.date = date;
    }
    public static MoodDTO fromEntity(Mood mood) {
        if (mood == null) return null;
        return new MoodDTO(
                mood.getId(),
                mood.getMoodLevel(),
                mood.getIntensity(),
                mood.getDate()
        );
    }
}