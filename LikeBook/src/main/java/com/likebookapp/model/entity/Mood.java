package com.likebookapp.model.entity;

import com.likebookapp.model.enums.EMood;

import jakarta.persistence.*;

@Entity
@Table(name = "moods")
public class Mood extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private EMood name;

    private String description;

    public EMood getName() {
        return name;
    }

    public void setName(EMood name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
