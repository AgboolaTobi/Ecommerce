package com.task1.ecommerce.data.models;


import africa.semicolon.shoppersDelight.dtos.response.ItemResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    @ManyToOne(cascade = {CascadeType.MERGE})
    private CustomerOrder customerOrder;

    public OrderLineItem(ItemResponse item, CustomerOrder customerOrder){
        this.name=item.getName();
        this.price=item.getPrice();
        this.quantity = item.getQuantity();
        this.customerOrder = customerOrder;
    }


}
