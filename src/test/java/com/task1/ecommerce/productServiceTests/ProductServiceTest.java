package com.task1.ecommerce.productServiceTests;

import com.task1.ecommerce.data.models.Category;
import com.task1.ecommerce.dtos.requests.AddProductRequest;
import com.task1.ecommerce.dtos.responses.AddProductResponse;
import com.task1.ecommerce.exceptions.ExistingProductException;
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
    public void testThatProductCanBeAddedToSellerStore() throws StoreNotFoundException, SellerNotFoundException, ExistingProductException {
        AddProductRequest request = new AddProductRequest();
        request.setSellerEmail("tobi4tee@email.com");
        request.setStoreId(2L);
        request.setCategory(Category.ACCESSORIES);
        request.setName("iphone15");
        request.setDescription("Apple phone brand");
        request.setPrice(new BigDecimal(450000));
        request.setQuantity(15);

        AddProductResponse response = productService.addProduct(request);
        System.out.println(response);
        assertThat(response).isNotNull();
    }

    @Test
    public void testThatASellerCanAddMultipleProducts() throws StoreNotFoundException, SellerNotFoundException, ExistingProductException {
        AddProductRequest request = new AddProductRequest();
        request.setSellerEmail("tobi4tee@email.com");
        request.setStoreId(2L);
        request.setCategory(Category.ACCESSORIES);
        request.setName("Dell Laptops");
        request.setDescription("Foreign used Dell laptops");
        request.setPrice(new BigDecimal(280000));
        request.setQuantity(12);
        AddProductResponse response = productService.addProduct(request);
        System.out.println(response);
        assertThat(response).isNotNull();
    }


    @Test
    public void testThatWhenASellerAttemptsToAddAnAlreadyExistingProductToStoreExceptionISThrown(){
        AddProductRequest request = new AddProductRequest();
        request.setSellerEmail("tobi4tee@email.com");
        request.setStoreId(2L);
        request.setCategory(Category.ACCESSORIES);
        request.setName("iphone15");
        request.setDescription("Apple phone brand");
        request.setPrice(new BigDecimal(450000));
        request.setQuantity(5);
        assertThrows(ExistingProductException.class,()->productService.addProduct(request));

    }

}
