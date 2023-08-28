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
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Enumerated(EnumType.STRING)
    private Goal goal;
    @Positive
    private float cal;
    @Positive
    private float prot;
    @Positive
    private float fats;
    @Positive
    private float carbs;
}


