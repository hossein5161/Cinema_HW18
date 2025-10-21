package repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import model.Movie;
import repository.MovieRepository;
import util.Database_Connection;

import java.util.List;

public class MovieRepositoryImpl implements MovieRepository {

    @Override
    public void addMovie(Movie movie) {
        try (EntityManager em = Database_Connection.getEntityManager()) {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
            System.out.println("Movie added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add movie: " + e.getMessage());

        }
    }

    @Override
    public void updateMovie(Movie movie) {
        try (EntityManager em = Database_Connection.getEntityManager()) {
            em.getTransaction().begin();
            em.merge(movie);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void deleteMovie(Long id) {
        try (EntityManager em = Database_Connection.getEntityManager()) {
            em.getTransaction().begin();
            Movie m = em.find(Movie.class, id);
            if (m != null) {
                em.remove(m);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Movie getMovie(Long id) {
        try (EntityManager em = Database_Connection.getEntityManager()) {
            return em.find(Movie.class, id);
        }
    }

    @Override
    public List<Movie> listMovies() {
        try (EntityManager em = Database_Connection.getEntityManager()) {
            TypedQuery<Movie> query = em.createQuery("FROM Movie", Movie.class);
            return query.getResultList();
        }
    }
}
