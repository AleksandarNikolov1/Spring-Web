package com.example.supermarket.models.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "shops")
public class Shop extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String address;

    private String name;

    @ManyToOne
    private Town town;

    @OneToMany(mappedBy = "shop")
    private Set<Seller> sellers;

    @ManyToMany(mappedBy = "shops")
    private List<Product> products;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public Set<Seller> getSellers() {
        return sellers;
    }

    public void setSellers(Set<Seller> sellers) {
        this.sellers = sellers;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
