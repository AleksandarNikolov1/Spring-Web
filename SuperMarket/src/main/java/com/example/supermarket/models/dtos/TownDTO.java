package com.example.supermarket.models.dtos;

public class TownDTO {

    private String name;

    public TownDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
