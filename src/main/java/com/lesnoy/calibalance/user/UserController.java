package com.lesnoy.calibalance.user;

import com.lesnoy.calibalance.exception.UserAlreadyExistsException;
import com.lesnoy.calibalance.exception.UserNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        try {
            User user = userService.findUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
        } catch (UserAlreadyExistsException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> deleteUserByUsername(@PathVariable String username) {
        try {
            userService.deleteUserByUsername(username);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}