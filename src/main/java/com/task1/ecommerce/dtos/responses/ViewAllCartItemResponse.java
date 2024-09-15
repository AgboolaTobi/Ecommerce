package com.task1.ecommerce.dtos.responses;


import com.task1.ecommerce.data.models.CartItem;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class ViewAllCartItemResponse {
    private List<CartItem> cartItems;
}
