package com.lesnoy.calibalance.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
    @NotNull
    private String name;
    @JsonProperty("product_type")
    private ProductType productType;
    @JsonProperty("owner_name")
    private String ownerName;
    @PositiveOrZero
    private int grams;
    @PositiveOrZero
    private float cal;
    @PositiveOrZero
    private float prot;
    @PositiveOrZero
    private float fats;
    @PositiveOrZero
    private float carbs;

}
