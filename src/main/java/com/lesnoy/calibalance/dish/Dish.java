package com.lesnoy.calibalance.dish;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Table(name = "dish")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "id_creator")
    @JsonProperty("id_creator")
    private int idCreator;
    @NotNull
    private String name;
    @PositiveOrZero
    private float grams;
    @PositiveOrZero
    private float cal;
    @PositiveOrZero
    private float prot;
    @PositiveOrZero
    private float fats;
    @PositiveOrZero
    private float carbs;

}
