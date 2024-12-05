package com.example.battleships.controllers;

import com.example.battleships.models.dtos.ShipDTO;
import com.example.battleships.models.dtos.StartBattleDTO;
import com.example.battleships.services.ShipService;
import com.example.battleships.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final ShipService shipService;
    private final UserService userService;

    public HomeController(ShipService shipService, UserService userService) {
        this.shipService = shipService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/home")
    public String getHomePage(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        long loggedUserId = userService.findByUsername(userDetails.getUsername()).getId();

        List<ShipDTO> ownShips = this.shipService.getAllShipsOwnedBy(loggedUserId);
        List<ShipDTO> enemyShips = this.shipService.getAllShipsNotOwnedBy(loggedUserId);
        List<ShipDTO> sortedShips = this.shipService.getAllShipsSorted();

        model.addAttribute("ownShips", ownShips);
        model.addAttribute("enemyShips", enemyShips);
        model.addAttribute("sortedShips", sortedShips);
        model.addAttribute("startBattleDTO", new StartBattleDTO());

        return "home";
    }
}
