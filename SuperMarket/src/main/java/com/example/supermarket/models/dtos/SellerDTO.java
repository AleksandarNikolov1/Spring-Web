package com.example.supermarket.models.dtos;

import java.math.BigDecimal;

public class SellerDTO {
    private String firstName;
    private String lastName;
    private int age;
    private BigDecimal salary;
    private String shopName;
    private String fullName;

    public SellerDTO() {
    }

    public SellerDTO(String firstName, String lastName, int age, BigDecimal salary, String shopName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
        this.shopName = shopName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getFullName(){
        return firstName + " " + lastName;
    }
}
