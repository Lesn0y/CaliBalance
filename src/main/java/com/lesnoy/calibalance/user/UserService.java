package com.lesnoy.calibalance.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findUserByLogin(String login) throws Exception {
        return repository.findByLogin(login)
                .orElseThrow(Exception::new);
    }

    public User saveUser(User user) {
        return repository.save(user);
    }

    public void deleteUserByLogin(String login) throws Exception {
        User user = findUserByLogin(login);
        repository.delete(user);
    }
}
