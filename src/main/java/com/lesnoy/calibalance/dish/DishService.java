package com.lesnoy.calibalance.dish;

import com.lesnoy.calibalance.exception.EmptyCollectionException;
import com.lesnoy.calibalance.exception.UserNotFoundException;
import com.lesnoy.calibalance.user.User;
import com.lesnoy.calibalance.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;
    private final UserService userService;

    public List<Dish> findAllDishesByOwnerLogin(String login) throws UserNotFoundException, EmptyCollectionException {
        User owner = userService.findUserByLogin(login);
        List<Dish> dishes = dishRepository.findAllByIdCreator(owner.getId());
        if (dishes.isEmpty()) {
            throw new EmptyCollectionException("The user has not saved any dishes yet");
        }
        return dishes;
    }

    public Dish saveProduct(Dish dish) {
        return dishRepository.save(dish);
    }

}
