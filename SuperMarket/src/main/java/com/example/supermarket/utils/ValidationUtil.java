package com.example.supermarket.utils;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public interface ValidationUtil {

    <E> boolean isValid(E entity);
    <E> Set<ConstraintViolation<E>> violations(E entity);

}
