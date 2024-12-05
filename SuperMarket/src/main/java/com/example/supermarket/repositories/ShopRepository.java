package com.example.supermarket.repositories;

import com.example.supermarket.models.entities.Product;
import com.example.supermarket.models.entities.Seller;
import com.example.supermarket.models.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

     Shop findByName(String name);

     @Query("SELECT s.products FROM Shop s WHERE s.name = :name")
     List<Product> findProductsByShopName(@Param("name") String name);

     @Query("SELECT s.sellers FROM Shop s WHERE s.name = :name")
     List<Seller> findSellersByShopName(@Param("name") String name);
}
