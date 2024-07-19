package com.task1.ecommerce.dtos.responses;

import com.task1.ecommerce.data.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SearchForProductResponse {

    private List<Product> products;
}

