package repository.impl;
import jakarta.persistence.EntityManager;
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

    @Override
    public double getAverageRating(Long movieId) {
        try (EntityManager em = Database_Connection.getEntityManager()) {
            String query = "SELECT AVG(c.rating) FROM Comment c WHERE c.movie.id = :movieId";
            Double averageRating = (Double) em.createQuery(query)
                    .setParameter("movieId", movieId)
                    .getSingleResult();
            return averageRating != null ? averageRating : 0;
        }
    }

    @Override
    public List<Movie> searchMovies(String query) {
        try (EntityManager em = Database_Connection.getEntityManager()) {
            String jpql = "SELECT m FROM Movie m WHERE m.title LIKE :query OR m.genre LIKE :query";
            TypedQuery<Movie> queryObj = em.createQuery(jpql, Movie.class);
            queryObj.setParameter("query", "%" + query + "%");
            return queryObj.getResultList();
        }
    }
}
