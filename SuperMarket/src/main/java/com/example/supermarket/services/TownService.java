package com.example.supermarket.services;

import com.example.supermarket.models.dtos.TownDTO;
import com.example.supermarket.models.entities.Town;
import com.example.supermarket.repositories.TownRepository;
import com.example.supermarket.utils.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TownService {

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validator;

    public TownService(TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validator) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    public void addTown(TownDTO townDTO){

        Set<ConstraintViolation<TownDTO>> violations =
                validator.violations(townDTO);

        if (!violations.isEmpty()){
            violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        Town town = modelMapper.map(townDTO, Town.class);
        townRepository.save(town);
        System.out.println("Successfully added town!");
    }
}
