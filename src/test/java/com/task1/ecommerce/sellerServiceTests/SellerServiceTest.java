package com.task1.ecommerce.sellerServiceTests;

import com.task1.ecommerce.dtos.requests.OpenMultipleSellerStoresRequest;
import com.task1.ecommerce.dtos.requests.SellerRegistrationRequest;
import com.task1.ecommerce.dtos.responses.OpenMultipleSellerStoresResponse;
import com.task1.ecommerce.dtos.responses.SellerRegistrationResponse;
import com.task1.ecommerce.exceptions.SellerNotFoundException;
import com.task1.ecommerce.exceptions.SellerRegistrationException;
import com.task1.ecommerce.services.SellerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class SellerServiceTest {


    @Autowired
    private SellerService sellerService;

    @Test
    public void testThatASellerCanRegister() throws SellerRegistrationException {
        SellerRegistrationRequest request = new SellerRegistrationRequest();
        request.setEmail("test@email.com");
        request.setPassword("password");
        request.setName("Test Name");
        request.setStoreName("Test Store");
        request.setStoreDescription("Test Store Description");

        SellerRegistrationResponse response = sellerService.registerSeller(request);
        assertThat(response).isNotNull();
    }

    @Test
    public void testThatMultipleSellersCanRegister() throws SellerRegistrationException {
        SellerRegistrationRequest request = new SellerRegistrationRequest();
        request.setEmail("tobi4tee@email.com");
        request.setPassword("tob104@Me.");
        request.setName("Agboola Tobi");
        request.setStoreName("Grace Stores");
        request.setStoreDescription("Your best place to get all kinds of sweet candies");

        SellerRegistrationResponse response = sellerService.registerSeller(request);
        assertThat(response).isNotNull();
    }

    @Test
    public void testThatASellerCanHaveMultipleStores() throws SellerNotFoundException {
        OpenMultipleSellerStoresRequest request = new OpenMultipleSellerStoresRequest();
        request.setSellerEmail("tobi4tee@email.com");
        request.setSellerId(1L);
        request.setStoreName("HisGrace Pharmacy");
        request.setStoreDescription("HisGrace Pharmacy sells all kinds of pharmaceutical drugs");
        OpenMultipleSellerStoresResponse response = sellerService.openMoreStore(request);
        assertThat(response).isNotNull();

    }

    @Test
    public void testThatIfARegisteredSellerAttemptsToRegisterExceptionIsThrown(){
        SellerRegistrationRequest request = new SellerRegistrationRequest();
        request.setEmail("tobi4tee@email.com");
        request.setPassword("tob104@Me.");
        request.setName("Agboola Tobi");
        request.setStoreName("Grace Stores");
        request.setStoreDescription("Your best place to get all kinds of laptops and phones...");
        assertThrows(SellerRegistrationException.class,()->sellerService.registerSeller(request));

    }
}
