package com.example.linkedout.models.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeDTO {
    private String id;
    @NotNull(message = "Select birthday date.")
    @Past(message = "Date should be in the past.")
    private LocalDate birthDate;
    @NotNull(message = "Select education level.")
    private String educationLevel;
    @NotBlank(message = "Name can not be empty.")
    @Size(min = 2, message = "Name must be at least 2 characters.")
    private String firstName;
    @NotBlank(message = "Name can not be empty.")
    @Size(min = 2, message = "Name must be at least 2 characters.")
    private String lastName;
    @NotBlank(message = "Job title can not be empty.")
    private String jobTitle;
    @NotNull(message = "Salary can not be empty.")
    @Positive(message = "Salary must be positive.")
    private BigDecimal salary;

    private String companyName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
