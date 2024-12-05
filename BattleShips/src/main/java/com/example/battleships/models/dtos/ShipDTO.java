package com.example.battleships.models.dtos;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ShipDTO {

    private Long id;
    @NotBlank(message = "Name is required.")
    @Size(min = 2, max = 10, message = "Name must be between 2 and 10 characters.")
    private String name;
    @NotNull(message = "Health is required.")
    @Positive(message = "Health must be positive")
    private Integer health;
    @NotNull(message = "Power is required.")
    @Positive(message = "Power must be positive")
    private Integer power;

    @NotNull(message = "Date is required.")
    @PastOrPresent(message = "Date must be in the past or in the present.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;

    private int categoryType = -1;

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

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }
}
