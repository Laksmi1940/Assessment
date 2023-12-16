package com.immutlyproducts.services;

import com.immutlyproducts.dtos.PriceUpdateDto;
import com.immutlyproducts.dtos.ProductDto;
import com.immutlyproducts.entities.Product;
import com.immutlyproducts.exceptions.InvalidPriceException;
import com.immutlyproducts.exceptions.RequestNotValidException;
import com.immutlyproducts.exceptions.MissingProductException;
import com.immutlyproducts.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductInteractionService {
    @Autowired
    private ProductRepository productRepository;


    //Get All Products
    public List<Product> getAllProducts(){
        List<Product> productList = productRepository.findAll();
        return productList;
    }

    // Get Products by its id
    public Product getProductsById(Integer id) throws MissingProductException {

        Product product = productRepository.findById(id).get();

        if(product == null){
            throw new MissingProductException("Product is not found with given id");
        }

        return product;
    }

    // Add a product
    public Product createProduct(ProductDto productDto) throws RequestNotValidException {

        Boolean duplicate = isDuplicateProduct(productDto);

        Boolean validPrice = isValidPrice(productDto);

        Boolean validRequest = isValidRequest(productDto);

        if(duplicate == false && validRequest == false && validPrice == false){

            Product product = new Product();
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setIsAvailable(productDto.getIsAvailable());
            productRepository.save(product);

            return product;
        }
        else{
            throw new RequestNotValidException("Invalid Request!!! Check Payload");
        }

    }

    //Check for valid request
    private Boolean isValidRequest(ProductDto productDto) throws RequestNotValidException {
        if(productDto.getIsAvailable() == null || productDto.getName() == null || productDto.getDescription() == null) {
            throw new RequestNotValidException("Payload has empty Fields..Please check it...");
        }

        return false;
    }

    //Check for valid price
    private Boolean isValidPrice(ProductDto productDto) throws RequestNotValidException {
        if(productDto.getPrice() < 0) {
            throw new RequestNotValidException("Price cant be Negative...");
        }
        return false;
    }

    //Check for duplicate
    private Boolean isDuplicateProduct(ProductDto productDto) throws RequestNotValidException {
        List<Product> productList = productRepository.findAll();

        for(Product product : productList){
            if(product.getName().equals(productDto.getName())){
                throw new RequestNotValidException("Product Already Available in DB...");
            }
        }

        return false;
    }

    // Update Product by its id
    public Product updateProduct(Integer id, ProductDto productDto) throws RequestNotValidException, MissingProductException {
        Product product = productRepository.findById(id).orElseThrow(() -> new MissingProductException("Product not found with given id"));


        Boolean validPrice = isValidPrice(productDto);
        Boolean validRequest = isValidRequest(productDto);

        if(validRequest == false && validPrice == false){

            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setIsAvailable(productDto.getIsAvailable());

            productRepository.save(product);
            return product;
        }
        else{
            throw new RequestNotValidException("Invalid Request!!! Check Payload");
        }
    }

    // Delete Product by id
    public String deleteProductById(Integer prodId) throws MissingProductException {

        Product product = getProductsById(prodId);

        productRepository.delete(product);
        return "Successfully Deleted!!!";
    }

    //Update prices of multiple products
    public List<Product> updatePriceMultipleProducts(List<PriceUpdateDto> priceUpdateDto) throws MissingProductException, InvalidPriceException {
        List<Product> list = new ArrayList<>();

        for(PriceUpdateDto payload : priceUpdateDto){

            Integer prodId = payload.getId();
            Double price = payload.getPrice();

            if(prodId == null){
                throw new MissingProductException("Product not found");
            }
            if(price < 0){
                throw new InvalidPriceException("Price cant be Negetive");
            }
            Product product = productRepository.findById(prodId).get();
            product.setPrice(price);

            productRepository.save(product);
            list.add(product);
        }
        return list;
    }
}
