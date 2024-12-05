package com.example.spotify.repositories;

import com.example.spotify.models.entities.Style;
import com.example.spotify.models.enums.EStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StyleRepository extends JpaRepository<Style, Long> {
    Style findByName(EStyle name);
}
