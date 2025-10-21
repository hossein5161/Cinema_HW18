package repository.impl;

import jakarta.persistence.EntityManager;
import model.Movie;
import model.User;
import model.Watchlist;
import repository.WatchlistRepository;
import util.Database_Connection;

import java.util.ArrayList;
import java.util.List;

public class WatchlistRepositoryImpl implements WatchlistRepository {

    @Override
    public void addToWatchlist(User user, Movie movie) {
        EntityManager em = Database_Connection.getEntityManager();
        try {
            em.getTransaction().begin();

            List<Watchlist> existingList = em.createQuery(
                            "FROM Watchlist w WHERE w.user = :user AND w.movie = :movie", Watchlist.class)
                    .setParameter("user", user)
                    .setParameter("movie", movie)
                    .getResultList();

            if (existingList.isEmpty()) {
                em.persist(new Watchlist(user, movie));
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void removeFromWatchlist(User user, Movie movie) {
        EntityManager em = Database_Connection.getEntityManager();
        try {
            em.getTransaction().begin();

            List<Watchlist> wlList = em.createQuery(
                            "FROM Watchlist w WHERE w.user = :user AND w.movie = :movie", Watchlist.class)
                    .setParameter("user", user)
                    .setParameter("movie", movie)
                    .getResultList();

            if (!wlList.isEmpty()) {
                em.remove(wlList.get(0));
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Movie> findWatchlistForUser(User user) {
        try (EntityManager em = Database_Connection.getEntityManager()) {
            return em.createQuery(
                            "SELECT w.movie FROM Watchlist w WHERE w.user = :user", Movie.class)
                    .setParameter("user", user)
                    .getResultList();
        }
    }

    @Override
    public long countWatchlistForUser(User user) {
        try (EntityManager em = Database_Connection.getEntityManager()) {
            return em.createQuery(
                            "SELECT COUNT(w) FROM Watchlist w WHERE w.user = :user", Long.class)
                    .setParameter("user", user)
                    .getSingleResult();
        }
    }

    @Override
    public List<Movie> getWatchlist(User user) {
        if (user == null) return new ArrayList<>();

        try (EntityManager em = Database_Connection.getEntityManager()) {
            return em.createQuery(
                            "SELECT w.movie FROM Watchlist w WHERE w.user = :user", Movie.class)
                    .setParameter("user", user)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
