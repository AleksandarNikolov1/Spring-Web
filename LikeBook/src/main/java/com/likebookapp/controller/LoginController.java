package com.likebookapp.controller;

import com.likebookapp.model.dto.LoginUserDTO;
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
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        if (!model.containsAttribute("userLoginModel")){
            model.addAttribute("userLoginModel", new LoginUserDTO());
        }

        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute("userLoginModel") LoginUserDTO userLoginDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userLoginModel", userLoginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginModel",
                    bindingResult);

            return "redirect:/login";
        }

        String loginError = userService.loginUser(userLoginDTO);

        if (loginError != null){
            redirectAttributes.addFlashAttribute("userLoginModel", userLoginDTO);
            redirectAttributes.addFlashAttribute("loginError", loginError);

            return "redirect:/login";
        }

        return "redirect:/home";
    }
}
