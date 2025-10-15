package service.impl;

import model.User;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import service.UserService;

public class UserServiceImpl implements UserService {
    UserRepository userRepository = new UserRepositoryImpl();
    @Override
    public User registerUser(User user) {
        return userRepository.registerUser(user);
    }
}
