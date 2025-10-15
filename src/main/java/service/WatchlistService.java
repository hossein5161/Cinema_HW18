package service;

import model.Movie;

import java.util.List;

public interface WatchlistService {
    void addMovieToUser(Long userId, Long movieId);
    List<Movie> getWatchlist(Long userId);
    void removeFromWatchlist(Long userId, Long movieId);
}
