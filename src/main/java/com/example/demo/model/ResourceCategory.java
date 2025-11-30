package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "resource_category")
@Getter
@Setter
@NoArgsConstructor
public class ResourceCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public ResourceCategory(String name) {
        this.name = name;
    }
    public String getResources()
    {
        return name;
    }

}
