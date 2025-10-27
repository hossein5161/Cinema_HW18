package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import model.Movie;
import model.User;
import service.MovieService;
import service.WatchlistService;
import service.impl.MovieServiceImpl;
import service.impl.WatchlistServiceImpl;

import java.io.IOException;
import java.util.List;

public class UserMoviesServlet extends HttpServlet {
    private final MovieService movieService = new MovieServiceImpl();
    private final WatchlistService watchlistService = new WatchlistServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String pathInfo = req.getPathInfo();

        if ("/dashboard".equals(pathInfo) || pathInfo == null) {
            List<Movie> movies = movieService.listMovies();
            req.setAttribute("movies", movies);
            req.getRequestDispatcher("/user-dashboard.jsp").forward(req, resp);
        } else if ("/watchlist".equals(pathInfo)) {
            List<Movie> watchlist = watchlistService.getWatchlist(user);
            req.setAttribute("watchlist", watchlist);
            req.getRequestDispatcher("/watchlist.jsp").forward(req, resp);
        } else if ("/movie/details".equals(pathInfo)) {
            Long movieId = Long.parseLong(req.getParameter("id"));
            Movie movie = movieService.getMovie(movieId);
            req.setAttribute("movie", movie);
            req.getRequestDispatcher("/movie-details.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String pathInfo = req.getPathInfo();

        if ("/watchlist/action".equals(pathInfo)) {
            Long movieId = Long.parseLong(req.getParameter("movieId"));
            String type = req.getParameter("type");
            Movie movie = movieService.getMovie(movieId);

            if (movie != null) {
                if ("add".equals(type)) {
                    watchlistService.add(movie, user);
                } else if ("remove".equals(type)) {
                    watchlistService.remove(movie, user);
                }
            }
            resp.sendRedirect("/user/watchlist");
        }
    }
}
