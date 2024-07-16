package com.task1.ecommerce.sellerServiceTests;

import com.task1.ecommerce.data.repositories.SellerRepository;
import com.task1.ecommerce.dtos.requests.SellerRegistrationRequest;
import com.task1.ecommerce.dtos.responses.SellerRegistrationResponse;
import com.task1.ecommerce.exceptions.SellerRegistrationException;
import com.task1.ecommerce.services.SellerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SellerServiceTest {

    @Autowired
    private SellerRepository sellerRepository;

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
        assertEquals(sellerRepository.count(), 1);
    }
}
