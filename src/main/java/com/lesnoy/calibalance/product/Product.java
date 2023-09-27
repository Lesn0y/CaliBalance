package com.lesnoy.calibalance.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "id_creator")
    @JsonProperty("id_creator")
    private int idCreator;
    private String name;
    @Column(name = "type_id")
    @Enumerated(EnumType.ORDINAL)
    @JsonProperty("product_type")
    private ProductType productType;
    private int grams = 100;
    private float cal;
    private float prot;
    private float fats;
    private float carbs;

    public Product(ProductDTO productDTO) {
        this.name = productDTO.getName();
        this.productType = productDTO.getProductType();
        this.grams = productDTO.getGrams();
        this.cal = productDTO.getCal();
        this.prot = productDTO.getProt();
        this.fats = productDTO.getFats();
        this.carbs = productDTO.getCarbs();
    }
}
