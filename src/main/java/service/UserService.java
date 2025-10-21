package service;

import model.Role;
import model.User;

import java.util.Optional;

public interface UserService {
    User register(String username, String email, String password,Role role);

    User login(String username, String password);

    User findById(Long id);

    void update(User user);

    User save(User user);

    User findByUsername(String username);
}
