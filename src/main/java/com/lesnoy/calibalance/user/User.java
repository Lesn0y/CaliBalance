package com.lesnoy.calibalance.user;

import com.lesnoy.calibalance.user.entry.Entry;
import com.lesnoy.calibalance.user.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String username;
    @Positive(message = "Age must be positive")
    private int age;
    @Positive(message = "Height must be positive")
    private float height;
    @Positive(message = "Weight must be positive")
    private float weight;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Enumerated(EnumType.STRING)
    private Goal goal;
    @Enumerated(EnumType.STRING)
    private Activity activity;
    @ManyToMany
    @JoinTable(name = "users_products",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Entry> entries;
    private float cal;
    private float prot;
    private float fats;
    private float carbs;
}


