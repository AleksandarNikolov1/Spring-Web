package com.example.spotify.seeders;

import com.example.spotify.models.entities.UserRole;
import com.example.spotify.models.enums.EUserRole;
import com.example.spotify.repositories.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserRoleSeeder implements CommandLineRunner {
    private final UserRoleRepository userRoleRepository;

    public UserRoleSeeder(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRoleRepository.count() == 0) {
            userRoleRepository.save(new UserRole(EUserRole.ADMIN));
            userRoleRepository.save(new UserRole(EUserRole.USER));
        }
    }
}
