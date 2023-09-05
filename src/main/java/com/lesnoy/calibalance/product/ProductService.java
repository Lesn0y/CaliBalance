package com.lesnoy.calibalance.product;

import com.lesnoy.calibalance.exception.UserNotFoundException;
import com.lesnoy.calibalance.user.User;
import com.lesnoy.calibalance.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final UserService userService;

    public List<Product> findAll() {
        return repository.findAll();
    }

    public List<Product> findAllProductsByOwner(String login) throws UserNotFoundException {
        Optional<User> userByLogin = userService.findUserByLogin(login);
        if (userByLogin.isEmpty()) {
            throw new UserNotFoundException("User with login " + login + " not exists");
        }
        return repository.findAllByIdCreator(userByLogin.get().getId());
    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

}
