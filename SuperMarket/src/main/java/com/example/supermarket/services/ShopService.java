package com.example.supermarket.services;

import com.example.supermarket.models.dtos.ProductDTO;
import com.example.supermarket.models.dtos.SellerDTO;
import com.example.supermarket.models.dtos.ShopDTO;
import com.example.supermarket.models.entities.Shop;
import com.example.supermarket.models.entities.Town;
import com.example.supermarket.repositories.ShopRepository;
import com.example.supermarket.repositories.TownRepository;
import com.example.supermarket.utils.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ShopService {
    private final ShopRepository shopRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validator;

    public ShopService(ShopRepository shopRepository, TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validator) {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    public void addShop(ShopDTO shopDTO){
        Set<ConstraintViolation<ShopDTO>> violations =
                validator.violations(shopDTO);

        if (!violations.isEmpty()){
            violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        Shop shop = modelMapper.map(shopDTO, Shop.class);

        Town town = townRepository.findByName(shopDTO.getTownName());
        shop.setTown(town);

        shopRepository.save(shop);
        System.out.println("Successfully added shop!");
    }

    public void showAllSellersInShop(String shopName){
        shopRepository
                .findSellersByShopName(shopName)
                .stream()
                .map(seller -> modelMapper.map(seller, SellerDTO.class))
                .forEach(sellerDTO -> System.out.println(sellerDTO.getFullName()));
    }

    public void showAllProductsInShop(String shopName){
        shopRepository
                .findProductsByShopName(shopName)
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .forEach(System.out::println);
    }

}
