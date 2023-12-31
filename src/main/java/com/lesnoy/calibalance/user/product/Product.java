package com.lesnoy.calibalance.user.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @Column(name = "type_id")
    @Enumerated(EnumType.ORDINAL)
    @JsonProperty("product_type")
    private ProductType productType;
    @PositiveOrZero
    private int grams = 100;
    @PositiveOrZero
    private float cal;
    @PositiveOrZero
    private float prot;
    @PositiveOrZero
    private float fats;
    @PositiveOrZero
    private float carbs;
}
