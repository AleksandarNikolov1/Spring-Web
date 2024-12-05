package com.example.supermarket.services;

import com.example.supermarket.models.dtos.SellerDTO;
import com.example.supermarket.models.entities.Seller;
import com.example.supermarket.models.entities.Shop;
import com.example.supermarket.repositories.SellerRepository;
import com.example.supermarket.repositories.ShopRepository;
import com.example.supermarket.utils.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validator;

    public SellerService(SellerRepository sellerRepository, ShopRepository shopRepository, ModelMapper modelMapper, ValidationUtil validator) {
        this.sellerRepository = sellerRepository;
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }


    public void addSeller(SellerDTO sellerDTO) {

        Set<ConstraintViolation<SellerDTO>> violations =
                validator.violations(sellerDTO);

        if (!violations.isEmpty()) {
            violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        Seller seller = modelMapper.map(sellerDTO, Seller.class);

        Shop shop = shopRepository.findByName(sellerDTO.getShopName());
        seller.setShop(shop);

        sellerRepository.save(seller);
        System.out.println("Successfully added seller!");
    }

    public void setManagerToSeller(String sellerFirstName, String sellerLastName,
                                   String managerFirstName, String managerLastName){
        Seller seller = sellerRepository.findByFirstNameAndLastName(sellerFirstName, sellerLastName);
        Seller manager = sellerRepository.findByFirstNameAndLastName(managerFirstName, managerLastName);

        if (seller != null && manager != null){
            seller.setManager(manager);
        }
    }
}
