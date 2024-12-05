package com.example.spotify.models.entities;

import com.example.spotify.models.enums.EUserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class UserRole extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EUserRole userRole;

    public UserRole() {

    }

    public UserRole(EUserRole userRole) {
        this.userRole = userRole;
    }

    public EUserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(EUserRole userRole) {
        this.userRole = userRole;
    }
}
