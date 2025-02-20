package com.example.spotify.repositories;

import com.example.spotify.models.entities.UserRole;
import com.example.spotify.models.enums.EUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByUserRole(EUserRole userRole);
}
