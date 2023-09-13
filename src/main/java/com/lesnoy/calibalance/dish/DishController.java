package com.lesnoy.calibalance.dish;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dishes")
public class DishController {

    private final DishService service;

    @GetMapping
    public ResponseEntity<List<Dish>> findAll(@RequestParam(name = "owner", required = false) String login) {
        List<Dish> dishes = service.findAllByOwnerLogin(login);
        return dishes == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dishes);
    }

    @PostMapping
    public ResponseEntity<Dish> saveProduct(@RequestBody @Valid Dish dish) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.saveProduct(dish));
    }
}
