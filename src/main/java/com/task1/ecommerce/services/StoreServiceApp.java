package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.Store;
import com.task1.ecommerce.data.repositories.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StoreServiceApp implements StoreService{
    private final StoreRepository storeRepository;

    @Override
    public void save(List<Store> stores) {
        storeRepository.saveAll(stores);
    }

    @Override
    public Store findById(Long storeId) {
        return storeRepository.findById(storeId).orElse(null);

    }
}
