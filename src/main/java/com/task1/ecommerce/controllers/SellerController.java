package com.task1.ecommerce.controllers;


import com.task1.ecommerce.dtos.requests.OpenMultipleSellerStoresRequest;
import com.task1.ecommerce.dtos.requests.SellerLoginRequest;
import com.task1.ecommerce.dtos.requests.SellerLogoutRequest;
import com.task1.ecommerce.dtos.requests.SellerRegistrationRequest;
import com.task1.ecommerce.dtos.responses.OpenMultipleSellerStoresResponse;
import com.task1.ecommerce.dtos.responses.SellerLoginResponse;
import com.task1.ecommerce.dtos.responses.SellerLogoutResponse;
import com.task1.ecommerce.dtos.responses.SellerRegistrationResponse;
import com.task1.ecommerce.exceptions.InvalidPhoneNumberException;
import com.task1.ecommerce.exceptions.SellerNotFoundException;
import com.task1.ecommerce.exceptions.SellerRegistrationException;
import com.task1.ecommerce.services.SellerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/seller/")
@AllArgsConstructor
public class SellerController {

    private final SellerService sellerService;



    @PostMapping("register")
    public ResponseEntity<SellerRegistrationResponse> registerSeller(@RequestBody SellerRegistrationRequest request) throws SellerRegistrationException, InvalidPhoneNumberException {
        return new ResponseEntity<>(sellerService.registerSeller(request), HttpStatus.CREATED);
    }

    @PostMapping("sellerLogin")
    public ResponseEntity<SellerLoginResponse> sellerLogin(@RequestBody SellerLoginRequest request) throws SellerNotFoundException {
        return new ResponseEntity<>(sellerService.sellerLogin(request), HttpStatus.OK);
    }

    @PostMapping("sellerLogout")
    public ResponseEntity<SellerLogoutResponse> sellerLogout(@RequestBody SellerLogoutRequest request) throws SellerNotFoundException {
        return new ResponseEntity<>(sellerService.sellerLogout(request), HttpStatus.OK);
    }

    @PostMapping("openStore")
    public ResponseEntity<OpenMultipleSellerStoresResponse> openStores(@RequestBody OpenMultipleSellerStoresRequest request) throws SellerNotFoundException {
        return new ResponseEntity<>(sellerService.openMoreStore(request),HttpStatus.CREATED);
    }


}
