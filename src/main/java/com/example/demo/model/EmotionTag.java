package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "emotion_tags")
public class EmotionTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = true)
    private String color;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToMany(mappedBy = "emotionTags")
    @JsonIgnore
    private Set<Journal> journals = new HashSet<>();

    public enum Category {
        POSITIVE, NEGATIVE, NEUTRAL, CUSTOM
    }

    public EmotionTag() {
    }

    public EmotionTag(String name, String color, Category category) {
        this.name = name;
        this.color = color;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}