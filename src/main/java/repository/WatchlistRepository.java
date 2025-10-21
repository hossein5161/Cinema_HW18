package repository;

import model.Movie;
import model.User;

import java.util.List;

public interface WatchlistRepository {
    void addToWatchlist(User user, Movie movie);
    void removeFromWatchlist(User user, Movie movie);
    List<Movie> findWatchlistForUser(User user);
    long countWatchlistForUser(User user);
    List<Movie> getWatchlist(User user);
}
