package com.task1.ecommerce.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class Store {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String companyName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
