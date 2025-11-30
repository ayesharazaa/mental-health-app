package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class crisisAlertDTO {

    private Long id;
    private String message;
    private String createdAt;
    private String username;
    private String entryText;
    private boolean crisisConfirmed;

    public crisisAlertDTO(Long id, String message, String createdAt, String username, String entryText, boolean crisisConfirmed) {
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
        this.username = username;
        this.entryText = entryText;
        this.crisisConfirmed = crisisConfirmed;

    }
}
