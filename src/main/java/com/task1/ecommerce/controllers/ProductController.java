package com.task1.ecommerce.controllers;

import com.task1.ecommerce.data.models.Product;
import com.task1.ecommerce.dtos.requests.AddProductRequest;
import com.task1.ecommerce.dtos.requests.SearchForProductByCategoryRequest;
import com.task1.ecommerce.dtos.requests.UpdateProductRequest;
import com.task1.ecommerce.dtos.responses.AddProductResponse;
import com.task1.ecommerce.dtos.responses.SearchForProductByCategoryResponse;
import com.task1.ecommerce.dtos.responses.UpdateProductResponse;
import com.task1.ecommerce.exceptions.ExistingProductException;
import com.task1.ecommerce.exceptions.ProductNotFoundException;
import com.task1.ecommerce.exceptions.SellerNotFoundException;
import com.task1.ecommerce.exceptions.StoreNotFoundException;
import com.task1.ecommerce.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product/")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("addProductToStore")
    public ResponseEntity<AddProductResponse> addProductToStore(@RequestBody AddProductRequest request) throws StoreNotFoundException, ExistingProductException, SellerNotFoundException {
        return new ResponseEntity<>(productService.addProduct(request), HttpStatus.CREATED);
    }

    @PostMapping("updateProduct")
    public ResponseEntity<UpdateProductResponse> updateProduct(@RequestBody UpdateProductRequest request) throws StoreNotFoundException, ExistingProductException, SellerNotFoundException, ProductNotFoundException {
        return new ResponseEntity<>(productService.updateProduct(request), HttpStatus.OK);
    }

    @GetMapping("getProductById")
    public ResponseEntity<Product> getProductById(@RequestParam Long productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }
    @GetMapping("getByProductCategory")
    public ResponseEntity<SearchForProductByCategoryResponse> getByProductCategory(@RequestBody SearchForProductByCategoryRequest request) {
        return new ResponseEntity<>(productService.getProductByCategory(request), HttpStatus.OK);
    }


}
