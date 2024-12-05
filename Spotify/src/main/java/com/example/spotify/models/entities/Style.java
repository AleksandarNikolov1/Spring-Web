package com.example.spotify.models.entities;

import com.example.spotify.models.enums.EStyle;
import jakarta.persistence.*;

@Entity
@Table(name = "styles")
public class Style extends BaseEntity{
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private EStyle name;

    @Column(columnDefinition = "TEXT")
    private String description;

    public EStyle getName() {
        return name;
    }

    public void setName(EStyle name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
