package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.Product;
import com.task1.ecommerce.data.models.Seller;
import com.task1.ecommerce.data.models.Store;
import com.task1.ecommerce.dtos.requests.AddProductRequest;
import com.task1.ecommerce.dtos.responses.AddProductResponse;
import com.task1.ecommerce.exceptions.ExistingProductException;
import com.task1.ecommerce.exceptions.SellerNotFoundException;
import com.task1.ecommerce.exceptions.StoreNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceApp implements ProductService{
    private final SellerService sellerService;
    private final StoreService storeService;

    @Override
    public AddProductResponse addProduct(AddProductRequest request) throws SellerNotFoundException, StoreNotFoundException, ExistingProductException {
        Seller existingSeller = verifySeller(request);
        List<Store> existingSellerStores = existingSeller.getStores();
        Store targetStore = verifyStore(request);
        List<Product> existingProductsInStore = targetStore.getProducts();
        for (Product existingProduct : existingProductsInStore)
            if (existingProduct.getName().equals(request.getName())) throw new ExistingProductException("Product already exists");
        Product newProduct = createProductToAdd(request, existingProductsInStore);
        targetStore.setProducts(existingProductsInStore);
        existingSellerStores.add(targetStore);
        storeService.save(existingSellerStores);
        return buildAddProductResponse(newProduct);
    }

    private Store verifyStore(AddProductRequest request) throws StoreNotFoundException {
        Store targetStore = storeService.findById(request.getStoreId());
        if (targetStore==null) throw new StoreNotFoundException("Store not found " + request.getStoreId());
        return targetStore;
    }

    private Seller verifySeller(AddProductRequest request) throws SellerNotFoundException {
        Seller existingSeller =sellerService.findByEmail(request.getSellerEmail());
        if (existingSeller == null) throw new SellerNotFoundException("Invalid Seller details");
        return existingSeller;
    }

    private static AddProductResponse buildAddProductResponse(Product newProduct) {
        AddProductResponse response = new AddProductResponse();
        response.setMessage("You have successfully added " + newProduct.getName() + " with the following product details: " +
                "Product name - " + newProduct.getName() + ", " +
                "Product description - " + newProduct.getDescription() + ", " +
                "Product price - " + newProduct.getPrice().toString() + ", " +
                "Product quantity - " + newProduct.getQuantity() + ".");
        return response;

    }

    private static Product createProductToAdd(AddProductRequest request, List<Product> existingProductsInStore) {
        Product newProduct = new Product();
        newProduct.setName(request.getName());
        newProduct.setPrice(request.getPrice());
        newProduct.setQuantity(request.getQuantity());
        newProduct.setProductCategory(request.getCategory());
        newProduct.setDescription(request.getDescription());
        newProduct.setCreatedAt(LocalDateTime.now());
        existingProductsInStore.add(newProduct);
        return newProduct;
    }
}
