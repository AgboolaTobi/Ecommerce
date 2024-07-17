package com.task1.ecommerce.dtos.requests;

import com.task1.ecommerce.data.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class UpdateProductRequest {
    private String sellerEmail;
    private Long storeId;
    private Long productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private Integer productQuantity;
    private Category category;
}
