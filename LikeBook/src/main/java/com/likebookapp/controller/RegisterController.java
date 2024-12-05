package com.likebookapp.controller;

import com.likebookapp.model.dto.RegisterUserDTO;
import com.likebookapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        if (!model.containsAttribute("userRegisterModel")){
            model.addAttribute("userRegisterModel", new RegisterUserDTO());
        }

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userRegisterModel") RegisterUserDTO userRegisterDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterModel", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterModel",
                    bindingResult);

            return "redirect:/register";
        }

        String registrationError = userService.registerUser(userRegisterDTO);

        if (registrationError != null) {
            redirectAttributes.addFlashAttribute("userRegisterModel", userRegisterDTO);
            redirectAttributes.addFlashAttribute("registerError", registrationError);
            return "redirect:/register";
        }

        else {
            return "redirect:/login";
        }
    }
}
