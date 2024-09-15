package com.task1.ecommerce.dtos.responses;

import com.task1.ecommerce.data.models.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class ViewAllProductsInStoreResponse {
    private List<Product> products;
}
