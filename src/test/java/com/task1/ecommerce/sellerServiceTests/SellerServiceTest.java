package com.task1.ecommerce.sellerServiceTests;

import com.task1.ecommerce.dtos.requests.OpenMultipleSellerStoresRequest;
import com.task1.ecommerce.dtos.requests.SellerLoginRequest;
import com.task1.ecommerce.dtos.requests.SellerLogoutRequest;
import com.task1.ecommerce.dtos.requests.SellerRegistrationRequest;
import com.task1.ecommerce.dtos.responses.OpenMultipleSellerStoresResponse;
import com.task1.ecommerce.dtos.responses.SellerLoginResponse;
import com.task1.ecommerce.dtos.responses.SellerLogoutResponse;
import com.task1.ecommerce.dtos.responses.SellerRegistrationResponse;
import com.task1.ecommerce.exceptions.InvalidCredentialsException;
import com.task1.ecommerce.exceptions.InvalidPhoneNumberException;
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
    public void testThatASellerCanRegister() throws SellerRegistrationException, InvalidPhoneNumberException {
        SellerRegistrationRequest request = new SellerRegistrationRequest();
        request.setEmail("tobi9tee@gmail.com");
        request.setPassword("tobi4Me$");
        request.setName("Agboola Tobi");
        request.setPhoneNumber("08139031085");
        request.setStoreName("Grace Stores");
        request.setStoreDescription("Your best place to get all kinds of sweet candies");
        SellerRegistrationResponse response = sellerService.registerSeller(request);
        assertThat(response).isNotNull();
    }


    @Test
    public void testThatMultipleSellersCanRegister() throws SellerRegistrationException, InvalidPhoneNumberException {
        SellerRegistrationRequest request = new SellerRegistrationRequest();
        request.setEmail("lanlehintife@gmail.com");
        request.setPassword("$Tifegrace04");
        request.setName("Lanlehin Tife");
        request.setPhoneNumber("08068952954");
        request.setStoreName("Tifeh Stores");
        request.setStoreDescription("Your best place to get all kinds of sweet candies");

        SellerRegistrationResponse response = sellerService.registerSeller(request);
        assertThat(response).isNotNull();
    }

    @Test
    public void tesThatAttemptToRegisterWithAnInvalidEmailFormatThrowsException(){
        SellerRegistrationRequest request = new SellerRegistrationRequest();
        request.setEmail("tobi4teeemail.com");
        request.setPassword("tob104@Me.");
        request.setName("Agboola Tobi");
        request.setStoreName("Grace Stores");
        request.setStoreDescription("Your best place to get all kinds of sweet candies");
        assertThrows(SellerRegistrationException.class, ()->sellerService.registerSeller(request));
    }

    @Test
    public void testThatAttemptToRegisterWithAnInvalidPasswordFormatThrowsException(){
        SellerRegistrationRequest request = new SellerRegistrationRequest();
        request.setEmail("tobi4tee@email.com");
        request.setPassword("tobi");
        request.setName("Agboola Tobi");
        request.setStoreName("Grace Stores");
        request.setStoreDescription("Your best place to get all kinds of sweet candies");
        assertThrows(SellerRegistrationException.class, ()->sellerService.registerSeller(request));
    }

    @Test
    public void testThatAttemptToRegisterWithAnInvalidNameFormatThrowsException(){
        SellerRegistrationRequest request = new SellerRegistrationRequest();
        request.setEmail("tobi4tee@email.com");
        request.setPassword("tob104@Me.");
        request.setName("345736 453353");
        request.setStoreName("Grace Stores");
        request.setStoreDescription("Your best place to get all kinds of sweet candies");
        assertThrows(SellerRegistrationException.class, ()->sellerService.registerSeller(request));
    }

    @Test
    public void testThatARegisteredSellerCanSellerLogin() throws SellerNotFoundException, InvalidCredentialsException {
        SellerLoginRequest request = new SellerLoginRequest();
        request.setEmail("tobi9tee@gmail.com");
        request.setPassword("tobi4Me$");

        SellerLoginResponse response = sellerService.sellerLogin(request);
        assertThat(response).isNotNull();
    }

    @Test
    public void testThatMultipleRegisteredSellersCanSellerLogin() throws SellerNotFoundException, InvalidCredentialsException {
        SellerLoginRequest request = new SellerLoginRequest();
        request.setEmail("lanlehintife@gmail.com");
        request.setPassword("$Tifegrace04");

        SellerLoginResponse response = sellerService.sellerLogin(request);
        assertThat(response).isNotNull();
    }

    @Test
    public void testThatSellerCanLogout() throws SellerNotFoundException {

        SellerLogoutRequest request = new SellerLogoutRequest();
        request.setSellerId(1L);
        SellerLogoutResponse response = sellerService.sellerLogout(request);

        assertThat(response).isNotNull();

    }

    @Test
    public void testThatASellerCanHaveMultipleStores() throws SellerNotFoundException {
        OpenMultipleSellerStoresRequest request = new OpenMultipleSellerStoresRequest();
        request.setSellerEmail("tobi9tee@gmail.com");
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
