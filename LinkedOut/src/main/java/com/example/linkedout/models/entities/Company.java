package com.example.linkedout.models.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity{
    @Column(nullable = false)
    private Long budget;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String town;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees;

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
