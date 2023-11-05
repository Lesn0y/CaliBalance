package com.lesnoy.calibalance.product;

import com.lesnoy.calibalance.exception.EmptyCollectionException;
import com.lesnoy.calibalance.exception.NoValuePresentException;
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

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> findAdminProducts(@RequestParam(name = "ordinalType", required = false) Integer ordinalType,
                                                           @RequestParam(name = "name", required = false) String name) {
        try {
            if (ordinalType == null) {
                return ResponseEntity.ok(productService.findAdminProductsByName(name));
            }
            if (name == null) {
                return ResponseEntity.ok(productService.findAdminProductsByType(ordinalType));
            }
            return ResponseEntity.badRequest().build();
        } catch (UserNotFoundException | EmptyCollectionException e) {
            log.info(e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{owner}")
    public ResponseEntity<List<Product>> findOwnerProducts(@PathVariable String owner,
                                                           @RequestParam(name = "ordinalType", required = false) Integer ordinalType,
                                                           @RequestParam(name = "name", required = false) String name) {
        try {
            if (ordinalType == null) {
                return ResponseEntity.ok(productService.findProductsByOwnerAndName(owner, name));
            }
            if (name == null) {
                return ResponseEntity.ok(productService.findProductsByOwnerAndType(owner, ordinalType));
            }
            return ResponseEntity.badRequest().build();
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (EmptyCollectionException e) {
            log.info(e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductsById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(productService.findById(id));
        } catch (NoValuePresentException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{username}")
    public ResponseEntity<Product> saveProductToUser(@PathVariable String username,
                                                     @RequestBody @Valid Product product) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProductToUser(product, username));
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{username}/{productId}")
    public ResponseEntity<Product> deleteProductFromUserMenu(@PathVariable String username,
                                                             @PathVariable int productId) {
        try {
            productService.deleteProductFromUser(productId, username);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException | NoValuePresentException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
