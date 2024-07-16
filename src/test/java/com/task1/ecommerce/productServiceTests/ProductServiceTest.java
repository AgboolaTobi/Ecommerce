package com.task1.ecommerce.productServiceTests;

import com.task1.ecommerce.data.models.Category;
import com.task1.ecommerce.dtos.requests.AddProductRequest;
import com.task1.ecommerce.dtos.responses.AddProductResponse;
import com.task1.ecommerce.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    public void testThatProductCanBeAddedToSellerStore() {
        AddProductRequest request = new AddProductRequest();
        request.setStoreId(2L);
        request.setCategory(Category.ACCESSORIES);
        request.setName("iphone");
        request.setDescription("Apple phone brand");
        request.setPrice(new BigDecimal(450000));
        request.setQuantity(15);

        AddProductResponse response = productService.addProduct(request);
        assertThat(response).isNotNull();

    }

}
