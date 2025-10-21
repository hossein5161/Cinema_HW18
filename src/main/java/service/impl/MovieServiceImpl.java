package service.impl;

import model.Movie;
import repository.MovieRepository;
import repository.impl.MovieRepositoryImpl;
import service.MovieService;

import java.util.List;

public class MovieServiceImpl implements MovieService {
    MovieRepository movieRepository = new MovieRepositoryImpl();

    @Override
    public void addMovie(Movie movie) {
        movieRepository.addMovie(movie);
    }

    @Override
    public void updateMovie(Movie movie) {
        if (movie.getId() == null) {
            throw new IllegalArgumentException("Movie ID is required for update");
        }
        movieRepository.updateMovie(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Movie ID is required for delete");
        }
        movieRepository.deleteMovie(id);
    }

    @Override
    public Movie getMovie(Long id) {
        return movieRepository.getMovie(id);
    }

    @Override
    public List<Movie> listMovies() {
        return movieRepository.listMovies();
    }
}
