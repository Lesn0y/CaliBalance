package com.lesnoy.calibalance.product;

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
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<Product>> findAllProducts(@RequestParam(name = "type") int typeId,
                                                         @RequestParam(name = "owner", required = false) String login) {
        try {
            if (login == null) {
                return ResponseEntity.ok(service.findAllProductsByType(typeId));
            }
            return ResponseEntity.ok(service.findAllProductsByOwnerAndType(login, typeId));
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (EmptyCollectionException e) {
            log.info(e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveProduct(product));
    }
}
