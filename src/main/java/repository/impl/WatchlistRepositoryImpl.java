package repository.impl;

import jakarta.persistence.EntityManager;
import model.Movie;
import model.User;
import repository.WatchlistRepository;
import util.Database_Connection;

import java.util.ArrayList;
import java.util.List;

public class WatchlistRepositoryImpl implements WatchlistRepository {

    @Override
    public void addMovieToUser(Long userId, Long movieId) {
       EntityManager em = Database_Connection.getEntityManager();
       try{
           em.getTransaction().begin();
           User user = em.find(User.class, userId);
           Movie movie = em.find(Movie.class, movieId);
           if(user!=null && movie!=null){
               user.getWatchlist().add(movie);
               em.merge(user);
           }
           em.getTransaction().commit();
       }catch(Exception e){
           em.getTransaction().rollback();
       }finally {
           em.close();
       }
    }

    @Override
    public List<Movie> getWatchlist(Long userId) {
        try (EntityManager em = Database_Connection.getEntityManager()) {
            User user = em.find(User.class, userId);
            if (user != null) {
                return new ArrayList<>(user.getWatchlist());
            }
            return null;
        }
    }

    @Override
    public void removeFromWatchlist(Long userId, Long movieId) {
        EntityManager em = Database_Connection.getEntityManager();
        try{
            em.getTransaction().begin();
            User user = em.find(User.class, userId);
            Movie movie = em.find(Movie.class, movieId);
            if(user!=null && movie!=null){
                user.getWatchlist().remove(movie);
                em.merge(user);
            }
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }finally {
            em.close();
        }
    }
}
