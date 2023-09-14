package com.lesnoy.calibalance.dish;

import com.lesnoy.calibalance.exception.EmptyCollectionException;
import com.lesnoy.calibalance.exception.UserNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dishes")
public class DishController {

    private final DishService service;

    @GetMapping
    public ResponseEntity<List<Dish>> findAllDishes(@RequestParam(name = "owner", required = false) String login) {
        try {
            return ResponseEntity.ok(service.findAllDishesByOwnerLogin(login));
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (EmptyCollectionException e) {
            log.info(e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<Dish> saveDishes(@RequestBody @Valid Dish dish) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveProduct(dish));
    }
}
