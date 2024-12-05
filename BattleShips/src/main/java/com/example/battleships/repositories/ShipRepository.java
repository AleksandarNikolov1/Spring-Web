package com.example.battleships.repositories;

import com.example.battleships.models.entities.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {
    List<Ship> findByUserId(long userId);
    List<Ship> findByUserIdNot(long ownerId);
    List<Ship> findByOrderByHealthAscNameDescPowerAsc();
    Optional<Ship> findByName(String name);
}
