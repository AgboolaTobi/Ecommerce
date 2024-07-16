package com.task1.ecommerce.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Set;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CascadeType.MERGE;
import static org.hibernate.annotations.CascadeType.PERSIST;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
    @OneToOne(fetch = EAGER, cascade = {CascadeType.MERGE})
    private Cart cart;
    @OneToMany(fetch = EAGER)
    @Cascade({PERSIST, MERGE})
    private List<Notification> notifications;
    @ElementCollection(fetch = EAGER)
    private Set<Role> roles;
}
