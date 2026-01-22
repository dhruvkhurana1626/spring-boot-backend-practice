package com.example.demo.model;

import com.example.demo.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Product {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private String id;

    @Column
    private String name;

    @Column
    private int price;

    @Column(length = 10,nullable = false,unique = true)
    private String phonenumber;

    @Enumerated(value = EnumType.STRING)
    Type type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="seller_id")
    Seller seller;

    @OneToMany(mappedBy = "product")
    List<Review> reviewList = new ArrayList<>();

    @ManyToMany
    @JoinTable
    List<OrderEntity> orderEntityList = new ArrayList<>();

}
