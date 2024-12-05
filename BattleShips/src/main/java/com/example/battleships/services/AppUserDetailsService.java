package com.example.battleships.services;

import com.example.battleships.models.entities.User;
import com.example.battleships.models.userdetails.AppUserDetails;
import com.example.battleships.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException
                        ("User with username " + username + " not found!"));
    }

    private UserDetails map(User user) {
        return new AppUserDetails(user);
    }
}
