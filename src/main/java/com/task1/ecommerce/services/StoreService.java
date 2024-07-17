package com.task1.ecommerce.services;

import com.task1.ecommerce.data.models.Store;

import java.util.List;

public interface StoreService {
    void save(List<Store> stores);

    Store findById(Long storeId);
}
