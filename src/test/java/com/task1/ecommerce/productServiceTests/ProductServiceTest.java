package com.task1.ecommerce.productServiceTests;

import com.task1.ecommerce.data.models.Category;
import com.task1.ecommerce.dtos.requests.AddProductRequest;
import com.task1.ecommerce.dtos.requests.UpdateProductRequest;
import com.task1.ecommerce.dtos.responses.AddProductResponse;
import com.task1.ecommerce.dtos.responses.UpdateProductResponse;
import com.task1.ecommerce.exceptions.ExistingProductException;
import com.task1.ecommerce.exceptions.ProductNotFoundException;
import com.task1.ecommerce.exceptions.SellerNotFoundException;
import com.task1.ecommerce.exceptions.StoreNotFoundException;
import com.task1.ecommerce.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    public void testThatProductCanBeAddedToSellerStore() throws StoreNotFoundException,
            SellerNotFoundException, ExistingProductException {
        AddProductRequest request = new AddProductRequest();
        request.setSellerEmail("tobi4tee@email.com");
        request.setStoreId(1L);
        request.setCategory(Category.CANDIES);
        request.setName("Sweets");
        request.setDescription("Creamy sweets for all ages...");
        request.setPrice(new BigDecimal(100));
        request.setQuantity(25);

        AddProductResponse response = productService.addProduct(request);
        System.out.println(response);
        assertThat(response).isNotNull();
    }

    @Test
    public void testThatASellerCanAddMultipleProducts() throws StoreNotFoundException,
            SellerNotFoundException, ExistingProductException {
        AddProductRequest request = new AddProductRequest();
        request.setSellerEmail("tobi4tee@email.com");
        request.setStoreId(2L);
        request.setCategory(Category.PHARMACY_ITEMS);
        request.setName("Antibiotics");
        request.setDescription("Antibiotics for all age group. Different brands in stock");
        request.setPrice(new BigDecimal(500));
        request.setQuantity(55);
        AddProductResponse response = productService.addProduct(request);
        System.out.println(response);
        assertThat(response).isNotNull();
    }


}
