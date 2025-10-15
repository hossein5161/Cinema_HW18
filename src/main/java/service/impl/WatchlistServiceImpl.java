package service.impl;

import model.Movie;
import repository.WatchlistRepository;
import repository.impl.WatchlistRepositoryImpl;
import service.WatchlistService;

import java.util.List;

public class WatchlistServiceImpl implements WatchlistService {
    WatchlistRepository watchlistRepository = new WatchlistRepositoryImpl();

    @Override
    public void addMovieToUser(Long userId, Long movieId) {
        watchlistRepository.addMovieToUser(userId, movieId);
    }

    @Override
    public List<Movie> getWatchlist(Long userId) {
        return watchlistRepository.getWatchlist(userId);
    }

    @Override
    public void removeFromWatchlist(Long userId, Long movieId) {
        watchlistRepository.removeFromWatchlist(userId, movieId);
    }
}
