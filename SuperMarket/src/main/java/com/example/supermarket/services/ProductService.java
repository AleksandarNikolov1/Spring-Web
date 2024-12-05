package com.example.supermarket.services;

import com.example.supermarket.models.dtos.ProductDTO;
import com.example.supermarket.models.entities.Category;
import com.example.supermarket.models.entities.Product;
import com.example.supermarket.models.entities.Shop;
import com.example.supermarket.repositories.CategoryRepository;
import com.example.supermarket.repositories.ProductRepository;
import com.example.supermarket.repositories.ShopRepository;
import com.example.supermarket.utils.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ShopRepository shopRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validator;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ShopRepository shopRepository,
                          ModelMapper modelMapper,
                          ValidationUtil validator) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    public void addProduct(ProductDTO productDTO) {
        Set<ConstraintViolation<ProductDTO>> violations =
                validator.violations(productDTO);

        if (!violations.isEmpty()) {
            violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        Product product = modelMapper.map(productDTO, Product.class);

        LocalDate bestBeforeDate = LocalDate.parse(productDTO.getBestBefore(),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Category category = categoryRepository.findByName(productDTO.getCategoryName());

        product.setBestBefore(bestBeforeDate);
        product.setCategory(category);

        productRepository.save(product);
        System.out.println("Successfully added product!");
    }

    public void productDistribution(String productName, String[] shopNames) {
        Product product = productRepository.findByName(productName);

        if (product != null && shopNames != null) {

            List<Shop> shops = new ArrayList<>();

            for (String shopName : shopNames) {
                Shop shop = shopRepository.findByName(shopName);

                if (shop != null) {
                    shops.add(shop);
                    shop.setProducts(List.of(product));
                    shopRepository.save(shop);
                }
            }

            product.setShops(shops);
            productRepository.save(product);

            System.out.println("Successfully added product distribution!");
        }
    }
}
