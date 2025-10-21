package service.impl;

import model.Role;
import model.*;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import service.UserService;
import util.PasswordUtil;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public User register(String username, String email, String password,Role role) {
        if (username == null || password == null || email == null|| role == null) {
            throw new RuntimeException("Invalid username or password");
        }
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("Invalid Username");
        }
        String hashPassword = PasswordUtil.encode(password);

        User user = User.builder().username(username)
                .passwordHash(hashPassword)
                .role(role).email(email)
                .build();

        return userRepository.save(user);
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }



    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        if (username == null || password == null) {
            throw new RuntimeException("Invalid username or password");
        }
        User foundUser = userRepository.findByUsername(username);
        if (foundUser == null) {
            throw new RuntimeException("Invalid username or password2");
        }
        if (!PasswordUtil.verify(password, foundUser.getPasswordHash())) {
            throw new RuntimeException("Invalid username or password3");
        }
        return foundUser;
    }
}
