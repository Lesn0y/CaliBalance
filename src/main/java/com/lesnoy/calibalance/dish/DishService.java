package com.lesnoy.calibalance.dish;

import com.lesnoy.calibalance.user.User;
import com.lesnoy.calibalance.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository repository;
    private final UserService userService;

    public List<Dish> findAllByOwnerLogin(String login) {
        Optional<User> owner = userService.findUserByLogin(login);
        return owner.map(user -> repository.findAllByIdCreator(user.getId())).orElse(null);
    }

    public Dish saveProduct(Dish dish) {
        return repository.save(dish);
    }

}
