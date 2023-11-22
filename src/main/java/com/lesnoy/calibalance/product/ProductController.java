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
    public ResponseEntity<List<Product>> findAdminProducts(@RequestParam(name = "owner", required = false) String owner,
                                                           @RequestParam(name = "type_id", required = false) Integer ordinalType,
                                                           @RequestParam(name = "name", required = false) String name) {
        log.info("Request GET \"/api/v1/products\" with 'owner='"+  owner + "', 'ordinalType='"+  ordinalType + "', 'name='"+  name + "' ACCEPTED");
        try {
            if (owner != null) {
                if (ordinalType == null) {
                    return ResponseEntity.ok(productService.findProductsByOwnerAndName(owner, name));
                }
                if (name == null) {
                    return ResponseEntity.ok(productService.findProductsByOwnerAndType(owner, ordinalType));
                }
            } else {
                if (ordinalType == null) {
                    return ResponseEntity.ok(productService.findAdminProductsByName(name));
                }
                if (name == null) {
                    return ResponseEntity.ok(productService.findAdminProductsByType(ordinalType));
                }
            }
            return ResponseEntity.badRequest().build();
        } catch (UserNotFoundException | EmptyCollectionException e) {
            log.info(e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductsById(@PathVariable int id) {
        log.info("Request GET \"/api/v1/products/{id}\" with 'id='"+  id + "' ACCEPTED");
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
        log.info("Request POST \"/api/v1/products/{username}\" with 'username='"+  username + "', product=' "+ product.getName() + "' ACCEPTED");
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
        log.info("Request DELETE \"/api/v1/products/{username}/productId\" with 'username='"+  username + "', productId=' "+ productId + "' ACCEPTED");
        try {
            productService.deleteProductFromUser(productId, username);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException | NoValuePresentException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
