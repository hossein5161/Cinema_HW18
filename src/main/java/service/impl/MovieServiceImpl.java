package service.impl;

import model.Movie;
import repository.MovieRepository;
import repository.impl.MovieRepositoryImpl;
import service.MovieService;

public class MovieServiceImpl implements MovieService {
    MovieRepository movieRepository = new MovieRepositoryImpl();

    @Override
    public Movie addMovie(Movie movie) {
        return movieRepository.addMovie(movie);
    }
}
