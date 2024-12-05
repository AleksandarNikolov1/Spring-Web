package com.example.battleships.controllers;

import com.example.battleships.models.dtos.ShipDTO;
import com.example.battleships.services.ShipService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ships")
public class ShipController {

    private final ShipService shipService;

    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping("/add")
    public String getAddShipPage(Model model) {
        if (!model.containsAttribute("addShipModel")){
            model.addAttribute("addShipModel", new ShipDTO());
        }

        return "ship-add";
    }

    @PostMapping("/add")
    public String addShip(@Valid @ModelAttribute("addShipModel") ShipDTO shipDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          @AuthenticationPrincipal UserDetails userDetails){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addShipModel", shipDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addShipModel",
                    bindingResult);

            return "redirect:/ships/add";
        }

        String shipCreationError = shipService.addShip(shipDTO, userDetails);

        if (shipCreationError != null){
            redirectAttributes.addFlashAttribute("addShipModel", shipDTO);
            redirectAttributes.addFlashAttribute("addShipError", shipCreationError);

            return "redirect:/ships/add";
        }

        return "redirect:/ships/add";
    }
}

