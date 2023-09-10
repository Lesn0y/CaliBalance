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

    public List<Product> findAllByType(Integer typeId) {
        return repository.findAllByIdCreatorAndProductType(0, ProductType.values()[typeId]);
    }

    public List<Product> findAllProductsByOwnerAndType(String login, Integer typeId) throws UserNotFoundException {
        Optional<User> userByLogin = userService.findUserByLogin(login);
        if (userByLogin.isEmpty()) {
            throw new UserNotFoundException("User with login " + login + " not exists");
        }
        return repository.findAllByIdCreatorAndProductType(userByLogin.get().getId(), ProductType.values()[typeId]);
    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

}
