package com.lesnoy.calibalance.product;

import com.lesnoy.calibalance.exception.UserNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<Product>> findAll(@RequestParam(name = "owner", required = false) String login) {
        if (login == null) {
            return ResponseEntity.ok(service.findAll());
        }
        try {
            return ResponseEntity.ok(service.findAllProductsByOwner(login));
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid Product product) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.saveProduct(product));
    }
}
