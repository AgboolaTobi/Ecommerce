package com.task1.ecommerce.dtos.responses;


import com.task1.ecommerce.data.models.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SearchForProductByPriceResponse {
    private List<Product> products;
}
