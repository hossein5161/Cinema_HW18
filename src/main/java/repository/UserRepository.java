package repository;

import model.User;

import java.util.Optional;

public interface UserRepository {
    User findById(Long id);
    void update(User user);
    User save(User user);
    User findByUsername(String username);



}
