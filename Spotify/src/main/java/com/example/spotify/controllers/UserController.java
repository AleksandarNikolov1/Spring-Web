package com.example.spotify.controllers;

import com.example.spotify.models.dtos.LoginUserDTO;
import com.example.spotify.models.dtos.RegisterUserDTO;
import com.example.spotify.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        if (!model.containsAttribute("userRegisterModel")) {
            model.addAttribute("userRegisterModel", new RegisterUserDTO());
        }

        return "register";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        if (!model.containsAttribute("userLoginModel")) {
            model.addAttribute("userLoginModel", new LoginUserDTO());
        }

        return "login";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userRegisterModel") RegisterUserDTO userRegisterDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterModel", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterModel",
                    bindingResult);

            return "redirect:/register";
        }

        String registrationError = userService.register(userRegisterDTO);

        if (registrationError != null) {
            redirectAttributes.addFlashAttribute("userRegisterModel", userRegisterDTO);
            redirectAttributes.addFlashAttribute("registerError", registrationError);
            return "redirect:/register";
        } else {
            redirectAttributes.addFlashAttribute("registerSuccess", "Registration successful. Please log in.");
            return "redirect:/login";
        }
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("userLoginModel") LoginUserDTO userLoginDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginModel", userLoginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginModel",
                    bindingResult);

            return "redirect:/register";
        }


        String loginError = userService.login(userLoginDTO);

        if (loginError != null) {
            redirectAttributes.addFlashAttribute("loginError", loginError);
            return "redirect:/login";
        }

        return "redirect:/home";
    }
}
