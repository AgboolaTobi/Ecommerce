package com.task1.ecommerce.dtos.requests;

import com.task1.ecommerce.data.models.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchForProductByCategoryRequest {
    private Category productCategory;
}
