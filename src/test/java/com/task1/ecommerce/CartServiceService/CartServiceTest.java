package com.task1.ecommerce.CartServiceService;

import com.task1.ecommerce.dtos.requests.AddToCartRequest;
import com.task1.ecommerce.dtos.responses.AddToCartResponse;
import com.task1.ecommerce.exceptions.BuyerNotFoundException;
import com.task1.ecommerce.exceptions.ProductNotFoundException;
import com.task1.ecommerce.services.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;
//    @Test
//    public void thatThatItemsCanBeAddedToTheCart() throws BuyerNotFoundException, ProductNotFoundException {
//        AddToCartRequest request = new AddToCartRequest();
//        request.setBuyerId(1L);
//        request.setProductId(2L);
//        request.setQuantity(3);
//
//        AddToCartResponse response = cartService.addProductToCart(request);
//        assertThat(response).isNotNull();
//
//    }
}
