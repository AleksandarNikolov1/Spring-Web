package com.example.battleships.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO {
    @NotBlank(message = "Username is required.")
    @Size(min = 3, max = 10, message = "Username must be between 3 and 10 characters.")
    private String username;
    @NotBlank(message = "Name is required.")
    @Size(min = 5, max = 20, message = "Name must be between 5 and 20 characters.")
    private String fullName;
    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email.")
    private String email;
    @NotBlank(message = "Password is required.")
    @Size(min = 3, message = "Password must be at least 3 characters.")
    private String password;
    @NotBlank(message = "Confirm password is required.")
    private String confirmPassword;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
