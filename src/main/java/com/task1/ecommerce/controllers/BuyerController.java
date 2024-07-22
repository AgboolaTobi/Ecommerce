package com.task1.ecommerce.controllers;


import com.task1.ecommerce.data.models.Buyer;
import com.task1.ecommerce.dtos.requests.AddToCartRequest;
import com.task1.ecommerce.dtos.requests.BuyerRegistrationRequest;
import com.task1.ecommerce.dtos.requests.OrderRequest;
import com.task1.ecommerce.dtos.requests.RemoveProductFromCartRequest;
import com.task1.ecommerce.dtos.responses.AddToCartResponse;
import com.task1.ecommerce.dtos.responses.BuyerRegistrationResponse;
import com.task1.ecommerce.dtos.responses.OrderResponse;
import com.task1.ecommerce.dtos.responses.RemoveProductFromCartResponse;
import com.task1.ecommerce.exceptions.*;
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
    public ResponseEntity<BuyerRegistrationResponse> register (@RequestBody BuyerRegistrationRequest request) throws BuyerExistException, BuyerRegistrationException {
        return new ResponseEntity<>(buyerService.registerBuyer(request), HttpStatus.CREATED);
    }

    @PostMapping("addProductToCart")
    public ResponseEntity<AddToCartResponse> addToCart(@RequestBody AddToCartRequest request) throws BuyerNotFoundException, ProductNotFoundException {
        return new ResponseEntity<>(buyerService.addProductToCart(request), HttpStatus.CREATED);
    }

    @PostMapping("removeItemFromCart")
    public ResponseEntity<RemoveProductFromCartResponse> removeFromCart(@RequestBody RemoveProductFromCartRequest request) throws BuyerNotFoundException, ProductNotFoundException, CartItemException {
        return new ResponseEntity<>(buyerService.removeProductFromCart(request),HttpStatus.OK);
    }

    @PostMapping("makeOrder")
    public ResponseEntity<OrderResponse> makeOrder(@RequestBody OrderRequest request) throws EmptyCartException, BuyerNotFoundException {
        return new ResponseEntity<>(buyerService.makeOrder(request), HttpStatus.CREATED);
    }

    @GetMapping("findBuyerById")
    public ResponseEntity<Buyer> findBuyerById(@RequestParam Long buyerId){
        return new ResponseEntity<>(buyerService.findByBuyerId(buyerId), HttpStatus.OK);
    }

}
