package com.immutlyproducts.controllers;

import com.immutlyproducts.dtos.PriceUpdateDto;
import com.immutlyproducts.dtos.ProductDto;
import com.immutlyproducts.entities.Product;
import com.immutlyproducts.exceptions.InvalidPriceException;
import com.immutlyproducts.exceptions.RequestNotValidException;
import com.immutlyproducts.exceptions.MissingProductException;
import com.immutlyproducts.services.ProductInteractionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/immutly")
public class ProductController {
    @Autowired
    private ProductInteractionService productInteractionService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> allProduct = productInteractionService.getAllProducts();
        return new ResponseEntity<List<Product>>(allProduct, HttpStatus.OK);
    }

    @GetMapping("/products/{product_id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer product_id) throws MissingProductException {
        Product product = productInteractionService.getProductsById(product_id);
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDto productDto) throws Exception {
        Product product = productInteractionService.createProduct(productDto);
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @PutMapping("/products/{product_id}")
    public ResponseEntity<Product> updateProduct(@Valid @PathVariable Integer product_id, @RequestBody ProductDto productDto) throws MissingProductException, RequestNotValidException {
        Product product = productInteractionService.updateProduct(product_id, productDto);
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @DeleteMapping("/products/{product_id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer product_id) throws MissingProductException {
        String response = productInteractionService.deleteProductById(product_id);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @PutMapping("/products/price_update")
    public ResponseEntity<List<Product>> updatePriceMultipleProducts(@RequestBody List<PriceUpdateDto> priceUpdateDto) throws InvalidPriceException, MissingProductException {
        List<Product> products = productInteractionService.updatePriceMultipleProducts(priceUpdateDto);
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);

    }
}
