package com.example.supermarket;

import com.example.supermarket.models.dtos.*;
import com.example.supermarket.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final SellerService sellerService;
    private final ShopService shopService;
    private final TownService townService;
    Scanner sc = new Scanner(System.in);

    public CommandLineRunnerImpl(CategoryService categoryService, ProductService productService, SellerService sellerService, ShopService shopService, TownService townService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.sellerService = sellerService;
        this.shopService = shopService;
        this.townService = townService;
    }

    @Override
    public void run(String... args) throws Exception {

        showOptions();

        while (true) {
            String input = sc.nextLine();

            while (!isValidInput(input)) {
                input = sc.nextLine();
            }

            int option = Integer.parseInt(input);

            switch (option) {
                case 1 -> addCategory();
                case 2 -> addTown();
                case 3 -> addShop();
                case 4 -> addSeller();
                case 5 -> addProduct();
                case 6 -> addManagerToSeller();
                case 7 -> productDistribution();
                case 8 -> showAllSellersInShop();
                case 9 -> showAllProductsInShop();
            }

            showOptions();
        }
    }

    private void addCategory() {
        System.out.println("Enter category name:");
        String categoryName = sc.nextLine();

        CategoryDTO categoryDTO = new CategoryDTO(categoryName);

        categoryService.addCategory(categoryDTO);
    }

    private void addTown() {
        System.out.println("Enter town name:");
        String townName = sc.nextLine();

        TownDTO townDTO = new TownDTO(townName);

        townService.addTown(townDTO);
    }

    private void addShop() {
        System.out.println("Enter shop details in format: name address town");
        String[] shopDetails = sc.nextLine().split("\\s+");

        String shopName = shopDetails[0];
        String address = shopDetails[1];
        String townName = shopDetails[2];

        ShopDTO shopDTO = new ShopDTO(shopName, address, townName);

        shopService.addShop(shopDTO);
    }

    private void addSeller() {
        System.out.println("Enter seller details in format: firsName lastName age salary shopName");

        String[] sellerDetails = sc.nextLine().split("\\s+");

        String firstName = sellerDetails[0];
        String lastName = sellerDetails[1];
        int age = Integer.parseInt(sellerDetails[2]);
        BigDecimal salary = new BigDecimal(sellerDetails[3]);
        String shopName = sellerDetails[4];

        SellerDTO sellerDTO = new SellerDTO(firstName, lastName, age, salary, shopName);

        sellerService.addSeller(sellerDTO);
    }

    private void addProduct() {
        System.out.println("Enter product details in format: name price bestBefore(dd-MM-yyyy) category");

        String[] productDetails = sc.nextLine().split("\\s+");

        String name = productDetails[0];
        BigDecimal price = new BigDecimal(productDetails[1]);
        String bestBeforeDate = productDetails[2];
        String categoryName = productDetails[3];

        ProductDTO productDTO = new ProductDTO(name, price, bestBeforeDate, categoryName);

        productService.addProduct(productDTO);
    }

    private void addManagerToSeller() {
        System.out.println("Enter seller first name and last names:");
        String sellerName = sc.nextLine();
        System.out.println("Enter manager first and last names:");
        String managerName = sc.nextLine();

        sellerService.setManagerToSeller(
                sellerName.split("\\s+")[0],
                sellerName.split("\\s+")[1],
                managerName.split("\\s+")[0],
                managerName.split("\\s+")[1]);

    }

    private void productDistribution() {
        System.out.println("Enter product name:");
        String productName = sc.nextLine();
        System.out.println("Enter product distribution in Shops names in format [shopName1 shopName2 ... ]:");
        String[] shopsName = sc.nextLine().split("\\s+");

        productService.productDistribution(productName, shopsName);
    }

    private void showAllSellersInShop() {
        System.out.println("Enter shop name:");
        String shopName = sc.nextLine();

        shopService.showAllSellersInShop(shopName);
    }

    private void showAllProductsInShop() {
        System.out.println("Enter shop name:");
        String shop = sc.nextLine();

        shopService.showAllProductsInShop(shop);
    }

    private void showOptions() {
        System.out.print("Hi\n");
        System.out.print("Choose option from:\n");
        System.out.print("1 - for Add Category\n");
        System.out.print("2 - for Add Town\n");
        System.out.print("3 - for Add Shop\n");
        System.out.print("4 - for Add Seller\n");
        System.out.print("5 - for Add Product\n");
        System.out.print("6 - for Set seller new manager\n");
        System.out.print("7 - for Distributing product in shops\n");
        System.out.print("8 - for Showing all sellers in Shop\n");
        System.out.print("9 - for Showing all products in Shop\n");
    }

    private boolean isValidInput(String input) {
        boolean isValid = false;
        String messageForInvalidInput = "Invalid input. Please try again.";

        try {
            int option = Integer.parseInt(input);

            if (option < 1 || option > 9)
                System.out.println(messageForInvalidInput);

            else
                isValid = true;

        } catch (NumberFormatException e) {
            System.out.println(messageForInvalidInput);
        }

        return isValid;
    }
}
