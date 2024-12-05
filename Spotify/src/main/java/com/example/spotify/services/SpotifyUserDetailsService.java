package com.example.spotify.services;

import com.example.spotify.models.entities.User;
import com.example.spotify.models.users.SpotifyUserDetails;
import com.example.spotify.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SpotifyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public SpotifyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with username " + username + " not found!"
                ));
    }

    private UserDetails map(User user){
        return new SpotifyUserDetails(user);
    }
}
