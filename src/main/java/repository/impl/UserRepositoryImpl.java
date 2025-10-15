package repository.impl;

import jakarta.persistence.EntityManager;
import model.User;
import repository.UserRepository;
import util.Database_Connection;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public User registerUser(User user) {
        EntityManager em = Database_Connection.getEntityManager();
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        try {
            em.getTransaction().begin();
            em.persist(newUser);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
        return newUser;
    }
}
