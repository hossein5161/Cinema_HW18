package repository.impl;

import jakarta.persistence.EntityManager;
import model.Movie;
import repository.MovieRepository;
import util.Database_Connection;

public class MovieRepositoryImpl implements MovieRepository {
    @Override
    public Movie addMovie(Movie movie) {
        EntityManager em = Database_Connection.getEntityManager();
        Movie newMovie = new Movie();
        newMovie.setTitle(movie.getTitle());
        newMovie.setGenre(movie.getGenre());
        newMovie.setDuration(movie.getDuration());
        try{
            em.getTransaction().begin();
            em.persist(newMovie);
            em.getTransaction().commit();
        }catch(Exception e){
            em.getTransaction().rollback();
        }finally{
            em.close();
        }
        return newMovie;
    }
}
