package com.example.supermarket.repositories;

import com.example.supermarket.models.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByFirstNameAndLastName(String firsName, String lastName);

    List<Seller> findByShopName(String shopName);
}
