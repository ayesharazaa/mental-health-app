package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class JournalDTO {
    private Long id;
    private String entryText;
    private java.util.List<String> emotionTags;
    private LocalDate date;
}