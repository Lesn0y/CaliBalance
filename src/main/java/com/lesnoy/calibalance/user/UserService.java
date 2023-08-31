package com.lesnoy.calibalance.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User findUserByLogin(String login) throws Exception {
        return repository.findByLogin(login)
                .orElseThrow(Exception::new);
    }

    public User saveUser(User user) {
        return repository.save(calculateStats(user));
    }

    public void deleteUserByLogin(String login) throws Exception {
        User user = findUserByLogin(login);
        repository.delete(user);
    }

    private User calculateStats(User user) {
        if (user.getSex().equals(Sex.MAN)) {
            user.setCal((10 * user.getWeight() + 6.25f * user.getHeight() - 5 * user.getAge() + 5) * user.getActivity().getMultiplier());
        } else if (user.getSex().equals(Sex.WOMAN)){
            user.setCal((10 * user.getWeight() + 6.25f * user.getHeight() - 5 * user.getAge() - 161) * user.getActivity().getMultiplier());
        }

        if (!user.getGoal().equals(Goal.KEEP_FIT)) {
            user.setCal(user.getCal() * user.getGoal().getCalMultiplier());
        }

        user.setProt(user.getCal() * user.getGoal().getProtMultiplier());
        user.setFats(user.getCal() * user.getGoal().getFatMultiplier());
        user.setCarbs(user.getCal() * user.getGoal().getCarbsMultiplier());

        return user;
    }
}
