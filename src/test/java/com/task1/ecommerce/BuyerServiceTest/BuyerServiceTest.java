package com.task1.ecommerce.BuyerServiceTest;
import com.task1.ecommerce.dtos.requests.*;
import com.task1.ecommerce.dtos.responses.*;
import com.task1.ecommerce.exceptions.*;
import com.task1.ecommerce.services.BuyerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BuyerServiceTest {

    @Autowired
    private BuyerService buyerService;
    @Test
    public void testThatABuyerCanRegister() throws BuyerExistException, BuyerRegistrationException {
        BuyerRegistrationRequest request = new BuyerRegistrationRequest();
        request.setEmail("avia02@gmail.com");
        request.setName("Avia Agboola");
        request.setPassword("avia02");
        request.setPhoneNumber("+2348068952954");
        request.setAddress("Jakande Estate,Ejigbo. Lagos");

        BuyerRegistrationResponse response = buyerService.registerBuyer(request);
        assertThat(response).isNotNull();
    }

    @Test
    public void testThatABuyerCanBuyerLogin() throws BuyerNotFoundException {
        BuyerLoginRequest request = new BuyerLoginRequest();
        request.setEmail("avia02@gmail.com");
        request.setPassword("avia02");
        BuyerLoginResponse response = buyerService.buyerLogin(request);
        assertThat(response).isNotNull();
    }

    @Test
    public void testThatBuyerCanLogout() throws BuyerNotFoundException {
        BuyerLogoutRequest request = new BuyerLogoutRequest();
        request.setId(1L);
        BuyerLogoutResponse response = buyerService.logoutBuyer(request);

        assertThat(response).isNotNull();

    }



    @Test
    public void testThatMultipleBuyersCanRegister() throws BuyerExistException, BuyerRegistrationException {
        BuyerRegistrationRequest request = new BuyerRegistrationRequest();
        request.setEmail("aduke05@gmail.com");
        request.setName("Aduke Agboola");
        request.setPassword("aduke02");
        request.setPhoneNumber("+2348068952954");
        request.setAddress("Ikoyi, Lagos");
        BuyerRegistrationResponse response = buyerService.registerBuyer(request);
        assertThat(response).isNotNull();
    }


    @Test
    public void testThatARegisteredBuyerCanAddProductToCart() throws BuyerNotFoundException, ProductNotFoundException {
        AddToCartRequest request = new AddToCartRequest();
        request.setBuyerId(1L);
        request.setProductId(1L);
        request.setQuantity(2);

        AddToCartResponse response = buyerService.addProductToCart(request);
        assertThat(response).isNotNull();
    }


    @Test
    public void testThatARegisteredBuyerCanAddTheSameProductToCartMultipleTimes() throws BuyerNotFoundException, ProductNotFoundException {
        AddToCartRequest request = new AddToCartRequest();
        request.setBuyerId(1L);
        request.setProductId(1L);
        request.setQuantity(3);

        AddToCartResponse response = buyerService.addProductToCart(request);
        assertThat(response).isNotNull();
    }


    @Test
    public void testThatABuyerCanAddMultipleProductsToCart() throws BuyerNotFoundException, ProductNotFoundException {
        AddToCartRequest request = new AddToCartRequest();
        request.setBuyerId(1L);
        request.setProductId(2L);
        request.setQuantity(1);

        AddToCartResponse response = buyerService.addProductToCart(request);
        assertThat(response).isNotNull();
    }



    @Test
    public void testThatMultipleRegisteredBuyersCanAddProductToCart() throws BuyerNotFoundException, ProductNotFoundException {
        AddToCartRequest request = new AddToCartRequest();
        request.setBuyerId(2L);
        request.setProductId(1L);
        request.setQuantity(2);

        AddToCartResponse response = buyerService.addProductToCart(request);
        assertThat(response).isNotNull();
    }

    @Test
    public void testThatMultipleRegisteredBuyersCanAddMultipleProductsToCart() throws BuyerNotFoundException, ProductNotFoundException {
        AddToCartRequest request = new AddToCartRequest();
        request.setBuyerId(2L);
        request.setProductId(2L);
        request.setQuantity(1);

        AddToCartResponse response = buyerService.addProductToCart(request);
        assertThat(response).isNotNull();
    }



    @Test
    public void testThatABuyerCanMakeAnOrder() throws BuyerNotFoundException, EmptyCartException {
        OrderRequest request = new OrderRequest();
        request.setBuyerId(1L);
        request.setDeliveryAddress("312, Sabo,Yaba,Lagos");
        request.setPhoneNumber("08139031085");
        request.setPreferredDeliveryDate("10/08/2024");
        OrderResponse response = buyerService.makeOrder(request);
        assertThat(response).isNotNull();

    }

    @Test
    public void testThatMultipleBuyersCanAddProductsToCart() throws BuyerNotFoundException, ProductNotFoundException {
        AddToCartRequest request = new AddToCartRequest();
        request.setBuyerId(2L);
        request.setProductId(1L);
        request.setQuantity(1);

        AddToCartResponse response = buyerService.addProductToCart(request);
        assertThat(response).isNotNull();
    }

//    @Test
//    public void testThatABuyerCanAddToTheQuantityOfASpecifiedProductInTheCart(){
//        AddToExistingCartItemsRequest request = new AddToExistingCartItemsRequest();
//
//    }


}
