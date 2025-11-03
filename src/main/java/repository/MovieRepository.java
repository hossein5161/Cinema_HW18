package repository;

import model.Movie;

import java.util.List;

public interface MovieRepository {
    void addMovie(Movie movie);

    void updateMovie(Movie movie);

    void deleteMovie(Long id);

    Movie getMovie(Long id);

    List<Movie> listMovies();

    double getAverageRating(Long movieId);

    List<Movie> searchMovies(String query);

}
