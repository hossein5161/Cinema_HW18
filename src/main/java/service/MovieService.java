package service;

import model.Movie;

import java.util.List;

public interface MovieService {
    void addMovie(Movie movie);

    void updateMovie(Movie movie);

    void deleteMovie(Long id);

    Movie getMovie(Long id);

    List<Movie> listMovies();

}
