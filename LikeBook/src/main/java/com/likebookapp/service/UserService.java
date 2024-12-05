package com.likebookapp.service;

import com.likebookapp.model.dto.LoginUserDTO;
import com.likebookapp.model.dto.RegisterUserDTO;
import com.likebookapp.model.entity.User;
import com.likebookapp.repository.UserRepository;
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

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public String registerUser(RegisterUserDTO userRegisterDTO){
        Optional<User> userByUsername = userRepository.findByUsername(userRegisterDTO.getUsername());
        Optional<User> userByEmail = userRepository.findByEmail(userRegisterDTO.getEmail());

        if (userByUsername.isPresent()){
            return "Username already exists.";
        }

        if (userByEmail.isPresent()){
            return "Email already registered.";
        }

        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())){
            return "Password and Confirm password do not match.";
        }

        User user = modelMapper.map(userRegisterDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        userRepository.save(user);

        return null;
    }

    public String loginUser(LoginUserDTO userLoginDTO) {
        try {
            UserDetails userDetails = userDetailsService
                    .loadUserByUsername(userLoginDTO.getUsername());

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    userDetails, userLoginDTO.getPassword(), userDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            return null;
        }

        catch (UsernameNotFoundException | BadCredentialsException e) {
            return "Invalid username or password.";
        }
    }
}
