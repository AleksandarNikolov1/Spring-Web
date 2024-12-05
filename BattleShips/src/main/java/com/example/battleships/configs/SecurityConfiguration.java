package com.example.battleships.configs;

import com.example.battleships.repositories.UserRepository;
import com.example.battleships.services.AppUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        // Everyone can download static resources (css, js, images)
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        // Everyone can log in and register
                        .requestMatchers("/", "/login", "/register").permitAll()
                        // All other pages are available for logged-in users
                        .anyRequest().authenticated()
                )

                // Configuration of form login
                .formLogin(formLogin -> formLogin
                        // The custom login form
                        .loginPage("/login")
                        // The name of the username and password form field
                        .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                        .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                        // Where to go when valid login
                        .defaultSuccessUrl("/home", true)
                        // Where to go when invalid login
                        .failureForwardUrl("/login")
                )

                // Configuration of logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new AppUserDetailsService(userRepository);
    }


}
