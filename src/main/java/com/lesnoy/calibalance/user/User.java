package com.lesnoy.calibalance.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String login;
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
    private float cal;
    private float prot;
    private float fats;
    private float carbs;
}


