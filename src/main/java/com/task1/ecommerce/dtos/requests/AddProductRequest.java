package com.task1.ecommerce.dtos.requests;


import com.task1.ecommerce.data.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AddProductRequest {
    private String sellerEmail;
    private Long sellerId;
    private Long storeId;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer quantity;
    private Category category;
}
