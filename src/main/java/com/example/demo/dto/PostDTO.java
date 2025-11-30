package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private String content;
    private boolean anonymous;
    private LocalDateTime date;
    private boolean flagged;
    private String flagReason;
    private String username; // Only shown if not anonymous
    public PostDTO(Long id, String content, boolean anonymous, LocalDateTime date, boolean flagged, String flagReason, String username) {
        this.id = id;
        this.content = content;
        this.anonymous = anonymous;
        this.date = date;
        this.flagged = flagged;
        this.flagReason = flagReason;
        this.username = username;
    }
}