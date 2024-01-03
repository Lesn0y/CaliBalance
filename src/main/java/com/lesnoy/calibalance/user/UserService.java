package com.lesnoy.calibalance.user;

import com.lesnoy.calibalance.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User findByUsername(String username) throws UserNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User with username '" + username + "' not exists"));
    }

    public User save(User user) {
        try {
            User oldUser = findByUsername(user.getUsername());
            oldUser.setAge(user.getAge());
            oldUser.setHeight(user.getHeight());
            oldUser.setWeight(user.getWeight());
            oldUser.setSex(user.getSex());
            oldUser.setGoal(user.getGoal());
            oldUser.setActivity(user.getActivity());
            return repository.save(calculateStats(oldUser));
        } catch (UserNotFoundException e) {
            return repository.save(calculateStats(user));
        }
    }

    public User updateUserCallInfo(String username, UserCallInfoDTO userCallInfo) throws UserNotFoundException {
        User user = findByUsername(username);
        user.setCal(userCallInfo.getCal());
        user.setProt(userCallInfo.getProt());
        user.setFats(userCallInfo.getFats());
        user.setCarbs(userCallInfo.getCarbs());
        return repository.save(user);
    }

    public void deleteByUsername(String username) throws UserNotFoundException {
        User user = repository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User with username '" + username + "' not exists"));
        repository.delete(user);
    }

    private User calculateStats(User user) {
        user.setCal((10 * user.getWeight() + 6.25f * user.getHeight() - 5 * user.getAge() + user.getSex().getArg())
                * user.getActivity().getMultiplier());

        if (!user.getGoal().equals(Goal.KEEP_FIT)) {
            user.setCal(user.getCal() + user.getCal() * user.getGoal().getCalMultiplier());
        }

        float tempCal = user.getCal();
        user.setProt(user.getGoal().getProtMultiplier() * user.getWeight());
        tempCal -= user.getProt() * 4;
        user.setFats(user.getGoal().getFatMultiplier() * user.getWeight());
        tempCal -= user.getFats() * 9;
        user.setCarbs(tempCal / 4);

        return user;
    }
}
