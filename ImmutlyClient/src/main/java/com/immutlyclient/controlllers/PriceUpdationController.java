package com.immutlyclient.controlllers;

import com.immutlyclient.dtos.PriceUpdationDto;
import com.immutlyclient.dtos.ProductResponse;
import com.immutlyclient.exceptions.InvalidPriceException;
import com.immutlyclient.exceptions.MissingProductException;
import com.immutlyclient.services.PriceUpdationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/immutly")
public class PriceUpdationController {
    @Autowired
    private PriceUpdationService priceUpdationService;

    @PutMapping("/priceUpdate")
    public ResponseEntity<List<ProductResponse>> updatePricesDoer(@RequestBody List<PriceUpdationDto> dtoList) throws MissingProductException, InvalidPriceException {
        List<ProductResponse> productResponses = priceUpdationService.updatePrices(dtoList);
        return new ResponseEntity<List<ProductResponse>>(productResponses, HttpStatus.OK);
    }
}
