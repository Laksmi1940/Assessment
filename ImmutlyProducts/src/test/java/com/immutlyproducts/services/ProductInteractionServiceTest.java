package com.immutlyproducts.services;

import com.immutlyproducts.dtos.PriceUpdateDto;
import com.immutlyproducts.dtos.ProductDto;
import com.immutlyproducts.entities.Product;
import com.immutlyproducts.exceptions.InvalidPriceException;
import com.immutlyproducts.exceptions.RequestNotValidException;
import com.immutlyproducts.exceptions.MissingProductException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductInteractionServiceTest {
    @Autowired
    private ProductInteractionService productInteractionService;


    @Test
    void getAllProduct() {
        List<Product> allProduct = productInteractionService.getAllProducts();
        assertEquals(allProduct.size(), allProduct.size());
        for(Product product : allProduct) {
            System.out.println(product.toString());
        }
    }

    @Test
    void getProductsById() throws MissingProductException {
        try {
            Product productById = productInteractionService.getProductsById(1);
            assertEquals(productById.getName(), "Hello");
            System.out.println(productById.toString());
        } catch (MissingProductException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createProduct() throws RequestNotValidException {
        ProductDto createProduct = new ProductDto();
        createProduct.setName("Name");
        createProduct.setDescription("Description");
        createProduct.setPrice(0.1);
        createProduct.setIsAvailable(true);

        productInteractionService.createProduct(createProduct);

        List<Product> allProduct = productInteractionService.getAllProducts();
        assertEquals(allProduct.size(), allProduct.size());

        for(Product product : allProduct){
            System.out.println(product.toString());
        }
    }

    @Test
    void updateProduct() throws MissingProductException, RequestNotValidException {

        List<Product> allProduct = productInteractionService.getAllProducts();

        Product productById = productInteractionService.getProductsById(2);
        productById.setName("Apple");
        productById.setDescription("IPhone 15");
        productById.setPrice(2000.00);
        productById.setIsAvailable(true);

        ProductDto productDto = new ProductDto();
        productDto.setName(productById.getName());
        productDto.setDescription(productById.getDescription());
        productDto.setPrice(productById.getPrice());
        productDto.setIsAvailable(productById.getIsAvailable());

        productInteractionService.updateProduct(2, productDto);



        Product updatedProduct = productInteractionService.getProductsById(2);
        assertEquals(updatedProduct.getName(), "Apple");
        assertEquals(updatedProduct.getDescription(), "IPhone 15");
        assertEquals(updatedProduct.getPrice(), 2000.00);
        assertEquals(updatedProduct.getIsAvailable(), true);
        System.out.println(updatedProduct.toString());
    }

    @Test
    void deleteProduct() throws MissingProductException {
        List<Product> allProduct = productInteractionService.getAllProducts();

        try {
            productInteractionService.deleteProductById(2);
        } catch (MissingProductException e) {
            throw new RuntimeException(e);
        }
        List<Product> allProductUpdated = productInteractionService.getAllProducts();
        assertEquals(allProductUpdated.size(), allProductUpdated.size());
        for(Product product : allProductUpdated){
            System.out.println(product.toString());
        }
    }
}