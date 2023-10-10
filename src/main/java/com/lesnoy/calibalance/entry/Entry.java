package com.lesnoy.calibalance.entry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lesnoy.calibalance.product.Product;
import com.lesnoy.calibalance.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "entries")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;
    @Column(name = "date")
    private Date date;
    @PositiveOrZero
    private int grams = 100;
    @JsonProperty("cal_left")
    @Column(name = "cal_left")
    private float calLeft;
    @JsonProperty("prot_left")
    @Column(name = "prot_left")
    private float protLeft;
    @JsonProperty("fats_left")
    @Column(name = "fats_left")
    private float fatsLeft;
    @JsonProperty("carbs_left")
    @Column(name = "carbs_left")
    private float carbsLeft;
}
