package com.example.spotify.services;

import com.example.spotify.models.dtos.LoginUserDTO;
import com.example.spotify.models.dtos.RegisterUserDTO;
import com.example.spotify.models.entities.User;
import com.example.spotify.models.entities.UserRole;
import com.example.spotify.models.enums.EUserRole;
import com.example.spotify.repositories.UserRepository;
import com.example.spotify.repositories.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public String register(RegisterUserDTO userRegisterDTO) {
        Optional<User> userByUsername = userRepository.findByUsername(userRegisterDTO.getUsername());
        Optional<User> userByEmail = userRepository.findByEmail(userRegisterDTO.getEmail());

        if (userByUsername.isPresent()) {
            return "Username already exists.";
        }

        if (userByEmail.isPresent()) {
            return "Email already exists.";
        }

        if (!userRegisterDTO.passwordsMatch()) {
            return "Password and confirm password do not match.";
        }

        User user = modelMapper.map(userRegisterDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        UserRole adminRole = userRoleRepository.findByUserRole(EUserRole.ADMIN)
                .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

        UserRole userRole = userRoleRepository.findByUserRole(EUserRole.USER)
                .orElseThrow(() -> new RuntimeException("USER role not found"));


        if (userRegisterDTO.getUsername().equals("admin")) {
            user.setUserRoles(List.of(adminRole, userRole));
        }

        else {
            user.setUserRoles(List.of(userRole));
        }

        userRepository.save(user);

        return null;
    }

    public String login(LoginUserDTO userLoginDTO) {
        try {
            UserDetails userDetails = userDetailsService
                    .loadUserByUsername(userLoginDTO.getUsername());

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    userDetails, userLoginDTO.getPassword(), userDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            return null;
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            return "Invalid username or password.";
        }
    }




}
