package com.lesnoy.calibalance.user;

import com.lesnoy.calibalance.exception.UserAlreadyExistsException;
import com.lesnoy.calibalance.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User findUserByUsername(String username) throws UserNotFoundException {
        Optional<User> user = repository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with username " + username + " not exists");
        }
        return user.get();
    }

    public User saveUser(User user) throws UserAlreadyExistsException {
        Optional<User> optUser = repository.findByUsername(user.getUsername());
        if (optUser.isPresent()) {
            throw new UserAlreadyExistsException("User with username " + user.getUsername() + " already exists");
        }
        return repository.save(calculateStats(user));
    }

    public void deleteUserByUsername(String username) throws UserNotFoundException {
        Optional<User> optUser = repository.findByUsername(username);
        if (optUser.isEmpty()) {
            throw new UserNotFoundException("User with username " + username + " not exists");
        }
        repository.delete(optUser.get());
    }

    private User calculateStats(User user) {
        if (user.getSex().equals(Sex.MAN)) {
            user.setCal((10 * user.getWeight() + 6.25f * user.getHeight() - 5 * user.getAge() + 5) * user.getActivity().getMultiplier());
        } else if (user.getSex().equals(Sex.WOMAN)){
            user.setCal((10 * user.getWeight() + 6.25f * user.getHeight() - 5 * user.getAge() - 161) * user.getActivity().getMultiplier());
        }

        if (!user.getGoal().equals(Goal.KEEP_FIT)) {
            user.setCal(user.getCal() + user.getCal() * user.getGoal().getCalMultiplier());
        }

        user.setProt(user.getCal() * user.getGoal().getProtMultiplier());
        user.setFats(user.getCal() * user.getGoal().getFatMultiplier());
        user.setCarbs(user.getCal() * user.getGoal().getCarbsMultiplier());

        return user;
    }
}
