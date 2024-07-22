package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.Product;
import com.task1.ecommerce.data.models.Seller;
import com.task1.ecommerce.data.models.Store;
import com.task1.ecommerce.data.repositories.SellerRepository;
import com.task1.ecommerce.dtos.requests.OpenMultipleSellerStoresRequest;
import com.task1.ecommerce.dtos.requests.SellerLoginRequest;
import com.task1.ecommerce.dtos.requests.SellerLogoutRequest;
import com.task1.ecommerce.dtos.requests.SellerRegistrationRequest;
import com.task1.ecommerce.dtos.responses.OpenMultipleSellerStoresResponse;
import com.task1.ecommerce.dtos.responses.SellerLoginResponse;
import com.task1.ecommerce.dtos.responses.SellerLogoutResponse;
import com.task1.ecommerce.dtos.responses.SellerRegistrationResponse;
import com.task1.ecommerce.exceptions.InvalidCredentialsException;
import com.task1.ecommerce.exceptions.SellerNotFoundException;
import com.task1.ecommerce.exceptions.SellerRegistrationException;
import com.task1.ecommerce.utils.Verification;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor

public class SellerServiceApp implements SellerService{

    private final SellerRepository sellerRepository;
    private final ModelMapper mapper;
    private final StoreService storeService;

    @Override
    public SellerRegistrationResponse registerSeller(SellerRegistrationRequest request) throws SellerRegistrationException {
        checkIfRegistered(request);
        Seller newSeller = createSeller(request);
        Store sellerStore = createSellerStore(request, newSeller);
        sellerRepository.save(newSeller);

        return buildSellerRegistrationResponse(newSeller, sellerStore);
    }

    @Override
    public OpenMultipleSellerStoresResponse openMoreStore(OpenMultipleSellerStoresRequest request) throws SellerNotFoundException {
        Seller existingSeller = getRegisteredSeller(request);
        List<Store> stores = existingSeller.getStores();

        createAdditionalStore(request, existingSeller, stores);
        sellerRepository.save(existingSeller);
        return buildAdditionalStoreResponse();
    }

    @Override
    public Seller findByEmail(String sellerEmail) {
        return sellerRepository.findByEmail(sellerEmail);
    }

    @Override
    public void save(Seller existingSeller) {
        sellerRepository.save(existingSeller);
    }

    @Override
    public SellerLoginResponse sellerLogin(SellerLoginRequest request) throws InvalidCredentialsException {
        Seller existingSeller = validateCredentials(request);
        existingSeller.setLogin(true);
        sellerRepository.save(existingSeller);
        return buildLoginResponse(existingSeller);
    }

    @Override
    public SellerLogoutResponse sellerLogout(SellerLogoutRequest request) throws SellerNotFoundException {
        Seller existingSeller = sellerRepository.getSellerById(request.getSellerId());
        if (existingSeller == null)throw new SellerNotFoundException("Seller not found");

        existingSeller.setLogin(false);
        sellerRepository.save(existingSeller);

        SellerLogoutResponse response = new SellerLogoutResponse();
        response.setMessage("Successfully logged out");
        return response;


    }

    @Override
    public Seller findSellerById(Long sellerId) {
        return sellerRepository.getSellerById(sellerId);
    }

    private SellerLoginResponse buildLoginResponse(Seller existingSeller) {
        SellerLoginResponse response = mapper.map(existingSeller, SellerLoginResponse.class);
        response.setMessage("Successfully logged in");
        return response;
    }

    private Seller validateCredentials(SellerLoginRequest request) throws InvalidCredentialsException {
        Seller existingSeller = sellerRepository.findByEmail(request.getEmail());
        if (existingSeller == null) throw new InvalidCredentialsException("Invalid email or password");
        boolean isValidPassword = BCrypt.checkpw(request.getPassword(), existingSeller.getPassword());
        if (!isValidPassword) throw new InvalidCredentialsException("Invalid email or password");
        return existingSeller;
    }

    private static OpenMultipleSellerStoresResponse buildAdditionalStoreResponse() {
        OpenMultipleSellerStoresResponse response = new OpenMultipleSellerStoresResponse();
        response.setMessage("Store successfully registered!");
        return response;
    }

    private void createAdditionalStore(OpenMultipleSellerStoresRequest request, Seller existingSeller, List<Store> stores) {
        Store newStore = new Store();
        newStore.setStoreName(request.getStoreName());
        newStore.setSellerId(existingSeller.getId());
        newStore.setStoreDescription(request.getStoreDescription());
        newStore.setStoreName(request.getStoreName());
        stores.add(newStore);
        existingSeller.setStores(stores);
        storeService.save(stores);
    }

    private Seller getRegisteredSeller(OpenMultipleSellerStoresRequest request) throws SellerNotFoundException {
        Seller existingSeller = sellerRepository.findByEmail(request.getSellerEmail());
        if (existingSeller == null) throw new SellerNotFoundException("Invalid seller details...");
        return existingSeller;
    }

    private static SellerRegistrationResponse buildSellerRegistrationResponse(Seller newSeller, Store sellerStore) {
        SellerRegistrationResponse response = new SellerRegistrationResponse();
        response.setMessage("Dear " + newSeller.getName() + " your registration was successful. Your store id is " + sellerStore.getId());
        return response;
    }

    private Store createSellerStore(SellerRegistrationRequest request, Seller newSeller) {
        List<Store> stores = new ArrayList<>();

        Store sellerStore = mapper.map(newSeller, Store.class);
        sellerStore.setStoreName(request.getStoreName());
        sellerStore.setStoreDescription(request.getStoreDescription());
        sellerStore.setSellerId(newSeller.getId());
        createProductsInStore(sellerStore);

        stores.add(sellerStore);
        storeService.save(stores);
        return sellerStore;
    }

    private static void createProductsInStore(Store sellerStore) {
        List<Product> products = new ArrayList<>();
        sellerStore.setProducts(products);
    }

    private Seller createSeller(SellerRegistrationRequest request) throws SellerRegistrationException {
        verifyDetails(request);
        Seller newSeller = mapper.map(request, Seller.class);
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        newSeller.setPassword(hashedPassword);
        newSeller.setCreatedAt(LocalDate.now());
        sellerRepository.save(newSeller);
        return newSeller;
    }





    private static void verifyDetails(SellerRegistrationRequest request) throws SellerRegistrationException {
        if (!Verification.verifyRegistrationEmail(request.getEmail()))
            throw new SellerRegistrationException("Invalid email format. Email should be in the format of example@gmail.com");

        if (!Verification.verifyRegistrationName(request.getName()))
            throw new SellerRegistrationException("Invalid name format: " + request.getName() + ". Name should contain letters and spaces only");

        if (!Verification.verifyPhoneNumber(request.getPhoneNumber()))
            throw new SellerRegistrationException("Invalid phone number format. Phone number should contain 11 digits only, without whitespaces");

        if (!Verification.verifyRegistrationPassword(request.getPassword()))
            throw new SellerRegistrationException(""" 
            Invalid password format. Ensure password has:
            - A minimum length of 8 characters and a maximum length of 16 characters
            - No whitespaces
            - A mix of:
              - Lowercase letters (a-z)
              - Uppercase letters (A-Z)
              - Digits (0-9)
              - Special characters (@, $, !, %, *, ?, &)
        """);

        if (!Verification.verifyStoreName(request.getStoreName()))
            throw new SellerRegistrationException("Invalid store name format. Store name should contain letters, numbers, and spaces only, with a minimum length of 3 and a maximum length of 50");

        if (!Verification.verifyStoreDescription(request.getStoreDescription()))
            throw new SellerRegistrationException("Invalid store description format. Store description should contain letters, numbers, and spaces only, with a minimum length of 10 and a maximum length of 200");
    }




    private void checkIfRegistered(SellerRegistrationRequest request) throws SellerRegistrationException {
        boolean isRegistered = sellerRepository.findByEmail(request.getEmail()) != null;
        if (isRegistered) throw new SellerRegistrationException("Registration details already taken");
    }
}
