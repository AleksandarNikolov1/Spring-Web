package com.example.supermarket.models.dtos;

import jakarta.validation.constraints.Size;

public class CategoryDTO {
    @Size(min = 2, message = "Name must be minimum two characters!")
    private String name;

    public CategoryDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
