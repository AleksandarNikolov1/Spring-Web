package com.likebookapp.service;

import com.likebookapp.model.entity.User;
import com.likebookapp.model.user.LikeBookUserDetails;
import com.likebookapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class LikeBookUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public LikeBookUserDetailsService(UserRepository userRepository) {
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
        return new LikeBookUserDetails(user);
    }
}
