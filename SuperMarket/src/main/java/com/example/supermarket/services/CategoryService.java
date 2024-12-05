package com.example.supermarket.services;

import com.example.supermarket.models.dtos.CategoryDTO;
import com.example.supermarket.models.entities.Category;
import com.example.supermarket.repositories.CategoryRepository;
import com.example.supermarket.utils.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validator;


    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validator) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    public void addCategory(CategoryDTO categoryDTO){

        Set<ConstraintViolation<CategoryDTO>> violations =
                validator.violations(categoryDTO);

        if (!violations.isEmpty()){
            violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

           return;
        }

        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryRepository.save(category);
        System.out.println("Successfully added category!");
    }
}
