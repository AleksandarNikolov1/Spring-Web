package com.example.supermarket.models.dtos;

import java.math.BigDecimal;

public class ProductDTO {
    private String name;
    private BigDecimal price;
    private String bestBefore;
    private String categoryName;

    public ProductDTO() {
    }

    public ProductDTO(String name, BigDecimal price, String bestBefore, String categoryName) {
        this.name = name;
        this.price = price;
        this.bestBefore = bestBefore;
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(String bestBefore) {
        this.bestBefore = bestBefore;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return String.format("%s - %.2f$", name, price);
    }
}
