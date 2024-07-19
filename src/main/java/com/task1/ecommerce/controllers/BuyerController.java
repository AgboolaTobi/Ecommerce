package com.task1.ecommerce.controllers;


import com.task1.ecommerce.data.models.Buyer;
import com.task1.ecommerce.dtos.requests.AddToCartRequest;
import com.task1.ecommerce.dtos.requests.BuyerPaymentRequest;
import com.task1.ecommerce.dtos.requests.BuyerRegistrationRequest;
import com.task1.ecommerce.dtos.requests.OrderRequest;
import com.task1.ecommerce.dtos.responses.AddToCartResponse;
import com.task1.ecommerce.dtos.responses.BuyerPaymentResponse;
import com.task1.ecommerce.dtos.responses.BuyerRegistrationResponse;
import com.task1.ecommerce.dtos.responses.OrderResponse;
import com.task1.ecommerce.exceptions.BuyerExistException;
import com.task1.ecommerce.exceptions.BuyerNotFoundException;
import com.task1.ecommerce.exceptions.EmptyCartException;
import com.task1.ecommerce.exceptions.ProductNotFoundException;
import com.task1.ecommerce.services.BuyerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/buyer/")
@AllArgsConstructor
public class BuyerController {

    private final BuyerService buyerService;

    @PostMapping("register")
    public ResponseEntity<BuyerRegistrationResponse> register (@RequestBody BuyerRegistrationRequest request) throws BuyerExistException {
        return new ResponseEntity<>(buyerService.registerBuyer(request), HttpStatus.CREATED);
    }

    @PostMapping("addProductToCart")
    public ResponseEntity<AddToCartResponse> addToCart(@RequestBody AddToCartRequest request) throws BuyerNotFoundException, ProductNotFoundException {
        return new ResponseEntity<>(buyerService.addProductToCart(request), HttpStatus.CREATED);
    }

    @PostMapping("makeOrder")
    public ResponseEntity<OrderResponse> makeOrder(@RequestBody OrderRequest request) throws EmptyCartException, BuyerNotFoundException {
        return new ResponseEntity<>(buyerService.makeOrder(request), HttpStatus.CREATED);
    }

    @GetMapping("findBuyerById")
    public ResponseEntity<Buyer> findBuyerById(@RequestParam Long buyerId) throws BuyerNotFoundException {
        return new ResponseEntity<>(buyerService.findByBuyerId(buyerId), HttpStatus.OK);
    }

}
