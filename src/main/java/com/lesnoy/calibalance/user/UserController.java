package com.lesnoy.calibalance.user;

import com.lesnoy.calibalance.exception.EmptyCollectionException;
import com.lesnoy.calibalance.exception.UserNotFoundException;
import com.lesnoy.calibalance.user.product.Product;
import com.lesnoy.calibalance.user.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/users")
public class UserController {

    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        log.info("Request GET \"\"/api/v1/users/username\" with 'username='" + username + "' ACCEPTED");
        try {
            return ResponseEntity.ok(userService.findByUsername(username));
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{username}/products")
    public ResponseEntity<List<Product>> findUserProducts(@PathVariable String username,
                                                          @RequestParam(name = "type-id", required = false) Integer ordinalType,
                                                          @RequestParam(name = "name", required = false) String name) {
        log.info("Request GET \"/{username}/products\" with 'username='" + username + "', 'ordinalType='" + ordinalType + "', 'name='" + name + "' ACCEPTED");
        try {
            if (ordinalType == null && name == null) {
                return ResponseEntity.ok(productService.findProductsByUsername(username));
            } else if (ordinalType == null) {
                return ResponseEntity.ok(productService.findProductsByUsernameAndName(username, name));
            }
            return ResponseEntity.ok(productService.findProductsByUsernameAndType(username, ordinalType));
        } catch (UserNotFoundException | EmptyCollectionException e) {
            log.info(e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
        log.info("Request POST \"/api/v1/users\" with 'user='" + user.getUsername() + "' ACCEPTED");
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> deleteUserByUsername(@PathVariable String username) {
        try {
            userService.deleteByUsername(username);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}