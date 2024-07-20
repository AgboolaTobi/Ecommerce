package com.task1.ecommerce.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SellerRegistrationException.class)
    public ResponseEntity<String> serviceFusionException(SellerRegistrationException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BuyerExistException.class)
    public ResponseEntity<String> serviceFusionException(BuyerExistException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BuyerNotFoundException.class)
    public ResponseEntity<String> serviceFusionException(BuyerNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BuyerRegistrationException.class)
    public ResponseEntity<String> serviceFusionException(BuyerRegistrationException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<String> serviceFusionException(EmptyCartException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistingProductException.class)
    public ResponseEntity<String> serviceFusionException(ExistingProductException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPhoneNumberException.class)
    public ResponseEntity<String> serviceFusionException(InvalidPhoneNumberException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> serviceFusionException(OrderNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> serviceFusionException(ProductNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SellerNotFoundException.class)
    public ResponseEntity<String> serviceFusionException(SellerNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<String> serviceFusionException(StoreNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);

    }
}
