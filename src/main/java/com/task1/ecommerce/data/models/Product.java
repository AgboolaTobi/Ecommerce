package com.task1.ecommerce.data.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;

@Entity
@Getter
@Setter
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer quantity;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private LocalDateTime updatedAt;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @Cascade({CascadeType.MERGE,CascadeType.DETACH})
    private Store store;
    @Enumerated
    private Category productCategory;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = now();
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = now();
    }
}
