package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.Seller;
import com.task1.ecommerce.data.models.Store;
import com.task1.ecommerce.dtos.requests.AddProductRequest;
import com.task1.ecommerce.dtos.responses.AddProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceApp implements ProductService{
    private final SellerService sellerService;
    private final StoreService storeService;

    @Override
    public AddProductResponse addProduct(AddProductRequest request) {
        Seller existingSeller =sellerService.findByEmail(request.getSellerEmail());
        if (existingSeller == null) throw new
        Store targetStore = storeService.findById(request.getStoreId());

    }
}
