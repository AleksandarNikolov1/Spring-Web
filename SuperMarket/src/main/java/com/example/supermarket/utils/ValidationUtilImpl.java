package com.example.supermarket.utils;

import org.springframework.stereotype.Component;

import jakarta.validation.*;
import java.util.Set;

@Component
public class ValidationUtilImpl implements ValidationUtil{

    private final Validator validator;

    public ValidationUtilImpl() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public <E> boolean isValid(E entity) {
        return validator.validate(entity).isEmpty();
    }

    @Override
    public <E> Set<ConstraintViolation<E>> violations(E entity) {
        return validator.validate(entity);
    }
}
