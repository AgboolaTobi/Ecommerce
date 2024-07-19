package com.task1.ecommerce.services;


import com.task1.ecommerce.data.models.Product;
import com.task1.ecommerce.dtos.requests.AddProductRequest;
import com.task1.ecommerce.dtos.requests.SearchForProductByCategoryRequest;
import com.task1.ecommerce.dtos.requests.SearchForProductRequest;
import com.task1.ecommerce.dtos.requests.UpdateProductRequest;
import com.task1.ecommerce.dtos.responses.AddProductResponse;
import com.task1.ecommerce.dtos.responses.SearchForProductByCategoryResponse;
import com.task1.ecommerce.dtos.responses.SearchForProductResponse;
import com.task1.ecommerce.dtos.responses.UpdateProductResponse;
import com.task1.ecommerce.exceptions.ExistingProductException;
import com.task1.ecommerce.exceptions.ProductNotFoundException;
import com.task1.ecommerce.exceptions.SellerNotFoundException;
import com.task1.ecommerce.exceptions.StoreNotFoundException;

public interface ProductService {
    AddProductResponse addProduct(AddProductRequest request) throws SellerNotFoundException, StoreNotFoundException, ExistingProductException;

    UpdateProductResponse updateProduct(UpdateProductRequest request) throws SellerNotFoundException, StoreNotFoundException, ProductNotFoundException;

    Product getProductById(Long productId);

    void saveProduct(Product existingProduct);

    SearchForProductResponse getProductByProductName(SearchForProductRequest request);

    SearchForProductByCategoryResponse getProductByCategory(SearchForProductByCategoryRequest request);
}
