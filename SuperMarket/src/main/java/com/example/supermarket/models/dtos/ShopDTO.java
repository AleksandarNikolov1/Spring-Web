package com.example.supermarket.models.dtos;

import jakarta.validation.constraints.Size;

public class ShopDTO {

    @Size(min = 2, message = "Address must be minimum two characters!")
    private String address;

    @Size(min = 2, message = "Shop name must be minimum two characters!")
    private String name;

    @Size(min = 2, message = "Town name must be minimum two characters!")
    private String townName;

    public ShopDTO(String name, String address, String townName) {
        this.address = address;
        this.name = name;
        this.townName = townName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }
}
