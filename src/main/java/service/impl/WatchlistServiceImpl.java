package service.impl;

import jakarta.persistence.EntityManager;
import model.Movie;
import model.User;
import repository.WatchlistRepository;
import repository.impl.WatchlistRepositoryImpl;
import service.WatchlistService;
import util.Database_Connection;

import java.util.List;

public class WatchlistServiceImpl implements WatchlistService {
    WatchlistRepository watchlistRepository = new WatchlistRepositoryImpl();

    @Override
    public void add(Movie movie, User user) {
        if (user == null || movie == null) {
            throw new IllegalArgumentException("User and Movie must not be null");
        }
        watchlistRepository.addToWatchlist(user, movie);
    }

    @Override
    public void remove(Movie movie, User user) {
        if (user == null || movie == null) {
            throw new IllegalArgumentException("User and Movie must not be null");
        }
        watchlistRepository.removeFromWatchlist(user, movie);
    }

    @Override
    public List<Movie> list(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        return watchlistRepository.findWatchlistForUser(user);
    }

    @Override
    public long count(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        return watchlistRepository.countWatchlistForUser(user);
    }

    @Override
    public List<Movie> getWatchlist(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        return watchlistRepository.getWatchlist(user);

    }
}
