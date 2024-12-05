package com.example.battleships.services;

import com.example.battleships.models.entities.Category;
import com.example.battleships.models.enums.ShipType;
import com.example.battleships.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findByName(ShipType name){
        return categoryRepository.findByName(name);
    }
}
