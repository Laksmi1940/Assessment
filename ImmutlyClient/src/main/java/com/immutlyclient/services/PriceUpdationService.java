package com.immutlyclient.services;

import com.immutlyclient.constants.PriceUpdationConstant;
import com.immutlyclient.dtos.PriceUpdationDto;
import com.immutlyclient.dtos.ProductResponse;
import com.immutlyclient.dtos.ProductDto;
import com.immutlyclient.exceptions.InvalidPriceException;
import com.immutlyclient.exceptions.MissingProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceUpdationService {
    @Autowired
    public WebClient webClient;

    //Update prices of multiple products
    public List<ProductResponse> updatePrices(List<PriceUpdationDto> dtoList) throws MissingProductException, InvalidPriceException {
        List<ProductResponse> list = new ArrayList<>();

        for(PriceUpdationDto payload : dtoList){
            Integer prodId = payload.getId();
            Double price = payload.getPrice();

            if(prodId == null){
                throw new MissingProductException("Product Not Found!!!");
            }
            if(price < 0){
                throw new InvalidPriceException("Enter a Valid Price, Price cant be Negative");
            }
            ProductDto currentProduct = webClient
                    .get()
                    .uri(PriceUpdationConstant.getProductById, prodId)
                    .retrieve()
                    .bodyToMono(ProductDto.class)
                    .block();

            currentProduct.setPrice(price);

            webClient
                    .put()
                    .uri(PriceUpdationConstant.updateProductById, prodId)
                    .syncBody(currentProduct)
                    .retrieve()
                    .bodyToMono(ProductDto.class)
                    .block();

            ProductResponse updatedProductResponse = webClient
                    .get()
                    .uri(PriceUpdationConstant.getProductById, prodId)
                    .retrieve()
                    .bodyToMono(ProductResponse.class)
                    .block();

            list.add(updatedProductResponse);
        }
        return list;
    }
}
