package repository;

import model.Movie;

import java.util.List;

public interface WatchlistRepository {
    void addMovieToUser(Long userId, Long movieId);
    List<Movie> getWatchlist(Long userId);
    void removeFromWatchlist(Long userId, Long movieId);
}
