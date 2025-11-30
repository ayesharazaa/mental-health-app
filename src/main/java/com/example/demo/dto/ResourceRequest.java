package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResourceRequest {

    private String title;
    private String description;
    private String category;
    private String link;
    private String fileUrl;
}

