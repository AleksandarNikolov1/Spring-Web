package com.example.battleships.services;

import com.example.battleships.models.dtos.ShipDTO;
import com.example.battleships.models.entities.Category;
import com.example.battleships.models.entities.Ship;
import com.example.battleships.models.entities.User;
import com.example.battleships.models.enums.ShipType;
import com.example.battleships.repositories.ShipRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipService {
    private final ShipRepository shipRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public ShipService(ShipRepository shipRepository, UserService userService, CategoryService categoryService, ModelMapper modelMapper) {
        this.shipRepository = shipRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    public String addShip(ShipDTO shipDTO, UserDetails userDetails) {

        Optional<Ship> shipByName =
                shipRepository.findByName(shipDTO.getName());

        if (shipByName.isPresent()) {
            return "Ship with name " + shipDTO.getName() + " already exists.";
        }

        ShipType type = switch (shipDTO.getCategoryType()) {
            case 1 -> ShipType.CARGO;
            case 2 -> ShipType.PATROL;
            default -> ShipType.BATTLE;
        };

        Category category = categoryService.findByName(type);
        User owner = userService.findByUsername(userDetails.getUsername());

        Ship ship = modelMapper.map(shipDTO, Ship.class);
        ship.setCategory(category);
        ship.setUser(owner);

        this.shipRepository.save(ship);

        return null;
    }


    public List<ShipDTO> getAllShipsOwnedBy(long ownerId) {
        return shipRepository.findByUserId(ownerId)
                .stream()
                .map(ship -> {
                    ShipDTO shipDTO = modelMapper.map(ship, ShipDTO.class);
                    shipDTO.setCategoryType(this.getShipCategoryOrdinal(ship));
                    return shipDTO;
                })
                .collect(Collectors.toList());
    }


    public List<ShipDTO> getAllShipsNotOwnedBy(long ownerId) {
        return this.shipRepository.findByUserIdNot(ownerId)
                .stream()
                .map(ship -> {
                    ShipDTO shipDTO = modelMapper.map(ship, ShipDTO.class);
                    shipDTO.setCategoryType(this.getShipCategoryOrdinal(ship));
                    return shipDTO;
                })
                .collect(Collectors.toList());
    }


    public List<ShipDTO> getAllShipsSorted() {
        return this.shipRepository.findByOrderByHealthAscNameDescPowerAsc()
                .stream()
                .map(ship -> {
                    ShipDTO shipDTO = modelMapper.map(ship, ShipDTO.class);
                    shipDTO.setCategoryType(this.getShipCategoryOrdinal(ship));
                    return shipDTO;
                })
                .collect(Collectors.toList());
    }

    private int getShipCategoryOrdinal(Ship ship){
        return switch (ship.getCategory().getName().name()) {
            case "BATTLE" -> 0;
            case "CARGO" -> 1;
            case "PATROL" -> 2;
            default -> -1;
        };
    }
}
