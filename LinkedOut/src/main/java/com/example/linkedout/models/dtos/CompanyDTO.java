package com.example.linkedout.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CompanyDTO {
    private String id;
    @NotBlank(message = "Name can not be empty.")
    @Size(min = 2, max = 10, message = "Name must be between 2 and 10 characters.")
    private String name;
    @NotBlank(message = "Town name can not be empty.")
    @Size(min = 2, max = 10, message = "Town must be between 2 and 10 characters.")
    private String town;
    @NotBlank(message = "Description can not be empty.")
    @Size(min = 10, message = "Description must be at least 10 characters.")
    private String description;

    @NotNull(message = "Budget can not be empty.")
    @Positive(message = "Budget must be positive.")
    private Long budget;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }
}
