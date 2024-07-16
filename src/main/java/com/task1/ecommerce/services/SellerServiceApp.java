package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.Seller;
import com.task1.ecommerce.data.models.Store;
import com.task1.ecommerce.data.repositories.SellerRepository;
import com.task1.ecommerce.dtos.requests.SellerRegistrationRequest;
import com.task1.ecommerce.dtos.responses.SellerRegistrationResponse;
import com.task1.ecommerce.exceptions.SellerRegistrationException;
import lombok.AllArgsConstructor;
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

    private static SellerRegistrationResponse buildSellerRegistrationResponse(Seller newSeller, Store sellerStore) {
        SellerRegistrationResponse response = new SellerRegistrationResponse();
        response.setMessage("Dear " + newSeller.getName() + " your registration was successful. Your store id is " + sellerStore.getId());
        return response;
    }

    private Store createSellerStore(SellerRegistrationRequest request, Seller newSeller) {
        List<Store> stores = new ArrayList<>();

        Store sellerStore = mapper.map(newSeller, Store.class);
        sellerStore.setStoreName(request.getStoreName());
        sellerStore.setSellerId(newSeller.getId());
        stores.add(sellerStore);
        storeService.save(stores);
        return sellerStore;
    }

    private Seller createSeller(SellerRegistrationRequest request) {
        Seller newSeller = mapper.map(request, Seller.class);
        newSeller.setStoreName(request.getStoreName());
        newSeller.setCreatedAt(LocalDate.now());
        sellerRepository.save(newSeller);
        return newSeller;
    }

    private void checkIfRegistered(SellerRegistrationRequest request) throws SellerRegistrationException {
        boolean isRegistered = sellerRepository.findByEmail(request.getEmail()) != null;
        if (isRegistered) throw new SellerRegistrationException("Registration details already taken");
    }
}
