package com.example.battleships.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserLoginDTO {
    @NotBlank(message = "Username is required.")
    @Size(min = 3, max = 10, message = "Username must be between 3 and 10 characters.")
    private String username;
    @NotBlank(message = "Password is required.")
    @Size(min = 3, message = "Password must be at least 3 characters.")
    private String password;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
