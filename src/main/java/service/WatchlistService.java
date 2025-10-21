package service;

import model.Movie;
import model.User;

import java.util.List;

public interface WatchlistService {
    void add(Movie movie, User user);
    void remove(Movie movie, User user);
    List<Movie> list(User user);
    long count(User user);
    List<Movie> getWatchlist(User user);
}
