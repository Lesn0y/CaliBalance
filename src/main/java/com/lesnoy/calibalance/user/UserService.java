package com.lesnoy.calibalance.user;

import com.lesnoy.calibalance.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User findByUsername(String username) throws UserNotFoundException {
        Optional<User> user = repository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with username '" + username + "' not exists");
        }
        return user.get();
    }

    public User save(User user) {
        return repository.save(calculateStats(user));
    }

    public void deleteByUsername(String username) throws UserNotFoundException {
        Optional<User> optUser = repository.findByUsername(username);
        if (optUser.isEmpty()) {
            throw new UserNotFoundException("User with username '" + username + "' not exists");
        }
        repository.delete(optUser.get());
    }

    private User calculateStats(User user) {
        user.setCal((10 * user.getWeight() + 6.25f * user.getHeight() - 5 * user.getAge() + user.getSex().getArg())
                * user.getActivity().getMultiplier());

        if (!user.getGoal().equals(Goal.KEEP_FIT)) {
            user.setCal(user.getCal() + user.getCal() * user.getGoal().getCalMultiplier());
        }
        user.setProt(user.getCal() * user.getGoal().getProtMultiplier());
        user.setFats(user.getCal() * user.getGoal().getFatMultiplier());
        user.setCarbs(user.getCal() * user.getGoal().getCarbsMultiplier());
        return user;
    }
}
