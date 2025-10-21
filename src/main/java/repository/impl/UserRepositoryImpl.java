package repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.User;
import org.hibernate.Session;
import repository.UserRepository;
import util.Database_Connection;

import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {



    @Override
    public User findById(Long id) {
        try (EntityManager em = Database_Connection.getEntityManager()) {
            return em.find(User.class, id);
        }
    }

    @Override
    public void update(User user) {
        try (EntityManager em = Database_Connection.getEntityManager()) {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public User save (User user) {
        EntityManager em = Database_Connection.getEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
        return user;
    }

    @Override
    public User findByUsername(String username) {
        EntityManager em = Database_Connection.getEntityManager();
        TypedQuery<User> query = em.createQuery("select u from User u where u.username = :user ", User.class);
        query.setParameter("user", username);

        User user;

        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }

        return user;
    }
}
