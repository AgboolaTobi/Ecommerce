package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.Product;
import com.task1.ecommerce.data.models.Seller;
import com.task1.ecommerce.data.models.Store;
import com.task1.ecommerce.data.repositories.ProductRepository;
import com.task1.ecommerce.dtos.requests.*;
import com.task1.ecommerce.dtos.responses.*;
import com.task1.ecommerce.exceptions.ExistingProductException;
import com.task1.ecommerce.exceptions.ProductNotFoundException;
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
    private final ProductRepository productRepository;

    @Override
    public AddProductResponse addProduct(AddProductRequest request) throws SellerNotFoundException, StoreNotFoundException, ExistingProductException {
        Seller existingSeller = verifySeller(request.getSellerId());
        List<Store> existingSellerStores = existingSeller.getStores();
        Store targetStore = verifyStore(request.getStoreId());
        List<Product> existingProductsInStore = targetStore.getProducts();

        for (Product existingProduct : existingProductsInStore) {
            if (existingProduct.getName().equals(request.getName()) && existingProduct.getSellerId().equals(existingSeller.getId())) {
                throw new ExistingProductException("Product already exists in your store");
            }
        }

        Product newProduct = createProductToAdd(request, existingProductsInStore, existingSeller);
        targetStore.setProducts(existingProductsInStore);
        existingSellerStores.add(targetStore);
        storeService.save(existingSellerStores);
        return buildAddProductResponse(newProduct);
    }


    @Override
    public UpdateProductResponse updateProduct(UpdateProductRequest request) throws SellerNotFoundException, StoreNotFoundException, ProductNotFoundException {
        Seller existingSeller = verifySeller(request.getSellerId());
        List<Store> existingSellerStores = existingSeller.getStores();
        Store targetStore = verifyStore(request.getStoreId());
        List<Product> existingProductsInStore = targetStore.getProducts();
        Product targetProduct = verifyProduct(request);
        createUpdatedProduct(request, targetProduct);
        productRepository.save(targetProduct);
        existingProductsInStore.add(targetProduct);
        targetStore.setProducts(existingProductsInStore);
        storeService.save(existingSellerStores);
        existingSeller.setStores(existingSellerStores);
        sellerService.save(existingSeller);
        return buildProductUpdateResponse(targetProduct);
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public void saveProduct(Product existingProduct) {
        productRepository.save(existingProduct);
    }

    @Override
    public SearchForProductResponse getProductByProductName(SearchForProductRequest request) {

        List<Product> products = productRepository.getProductByName(request.getName());

        SearchForProductResponse response = new SearchForProductResponse();
        response.setProducts(products);

        return response;

    }

    @Override
    public SearchForProductByCategoryResponse getProductByCategory(SearchForProductByCategoryRequest request) {

        List<Product> products = productRepository.getByProductCategory(request.getProductCategory());

        SearchForProductByCategoryResponse response = new SearchForProductByCategoryResponse();
        response.setProducts(products);
        return response;

    }

    @Override
    public RemoveProductFromStoreResponse removeProductFromStore(RemoveProductFromStoreRequest request) throws SellerNotFoundException, ProductNotFoundException {
        Seller existingSeller = sellerService.findSellerById(request.getSellerId());
        if (existingSeller == null) throw new SellerNotFoundException("Seller not found");

        List<Store> existingSellerStores = existingSeller.getStores();

        Store targetStore = storeService.findById(request.getStoreId());
        List<Product> existingProductsInStore = targetStore.getProducts();
        Product targetProduct = productRepository.findById(request.getProductId()).orElse(null);
        if (targetProduct == null) throw new ProductNotFoundException("Product not found");

        existingProductsInStore.remove(targetProduct);
        targetStore.setProducts(existingProductsInStore);
        storeService.save(existingSellerStores);
        existingSeller.setStores(existingSellerStores);
        sellerService.save(existingSeller);

        RemoveProductFromStoreResponse response = new RemoveProductFromStoreResponse();
        response.setMessage("Product : " + targetProduct.getId() + " removed from the store.");
        return response;
    }

    private static UpdateProductResponse buildProductUpdateResponse(Product targetProduct) {
        UpdateProductResponse response = new UpdateProductResponse();
        response.setMessage("Product " + targetProduct.getName() + " updated successfully");
        return response;
    }

    private static void createUpdatedProduct(UpdateProductRequest request, Product targetProduct) {
        targetProduct.setName(request.getProductName());
        targetProduct.setDescription(request.getProductDescription());
        targetProduct.setProductCategory(request.getCategory());
        targetProduct.setQuantity(request.getProductQuantity());
        targetProduct.setPrice(request.getProductPrice());
        targetProduct.setUpdatedAt(LocalDateTime.now());
    }

    private Product verifyProduct(UpdateProductRequest request) throws ProductNotFoundException {
        Product targetProduct = productRepository.findById(request.getProductId()).orElse(null);
        if (targetProduct == null) throw new ProductNotFoundException("Product not found");
        return targetProduct;
    }

    private Store verifyStore(Long storeId) throws StoreNotFoundException {
        Store targetStore = storeService.findById(storeId);
        if (targetStore==null) throw new StoreNotFoundException("Store not found " + storeId);
        return targetStore;
    }

    private Seller verifySeller(Long sellerId) throws SellerNotFoundException {
//        Seller existingSeller =sellerService.findByEmail(sellerEmail);
        Seller existingSeller =sellerService.findSellerById(sellerId);
        if (existingSeller == null) throw new SellerNotFoundException("Invalid Seller details");
        return existingSeller;
    }

    private static AddProductResponse buildAddProductResponse(Product newProduct) {
        AddProductResponse response = new AddProductResponse();
        response.setMessage("You have successfully added " + newProduct.getName() + " with the following product details: " +
                "Product name - " + newProduct.getName() + ", " +
                "Product description - " + newProduct.getDescription() + ", " +
                "Product price - N" + newProduct.getPrice().toString() + ", " +
                "Product quantity - " + newProduct.getQuantity() + ".");
        return response;

    }

    private static Product createProductToAdd(AddProductRequest request, List<Product> existingProductsInStore, Seller existingSeller) {
        Product newProduct = new Product();
        newProduct.setName(request.getName());
        newProduct.setSellerId(existingSeller.getId());
        newProduct.setPrice(request.getPrice());
        newProduct.setQuantity(request.getQuantity());
        newProduct.setProductCategory(request.getCategory());
        newProduct.setDescription(request.getDescription());
        newProduct.setCreatedAt(LocalDateTime.now());
        existingProductsInStore.add(newProduct);
        return newProduct;
    }
}
