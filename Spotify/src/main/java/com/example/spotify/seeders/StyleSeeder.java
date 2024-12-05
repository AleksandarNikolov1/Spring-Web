package com.example.spotify.seeders;

import com.example.spotify.models.entities.Style;
import com.example.spotify.models.enums.EStyle;
import com.example.spotify.repositories.StyleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StyleSeeder implements CommandLineRunner {

    private final StyleRepository styleRepository;

    public StyleSeeder(StyleRepository styleRepository) {
        this.styleRepository = styleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (styleRepository.count() == 0){
             Arrays.stream(EStyle.values()).forEach(s -> {
                 Style style = new Style();
                 style.setName(s);
                 style.setDescription(null);
                 styleRepository.save(style);
             });
        }
    }
}
