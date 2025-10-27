package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import model.Movie;
import model.Role;
import model.User;
import service.MovieService;
import service.impl.MovieServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Base64;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50
)
public class AdminMovieServlet extends HttpServlet {
    private final MovieService movieService = new MovieServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user.getRole() != Role.ADMIN) {
            resp.sendRedirect("/user/dashboard");
            return;
        }

        String action = req.getParameter("action");
        if ("edit".equals(action)) {
            Long movieId = Long.valueOf(req.getParameter("id"));
            Movie movie = movieService.getMovie(movieId);
            req.setAttribute("movie", movie);
            req.getRequestDispatcher("/edit-movie.jsp").forward(req, resp);
        } else if ("add".equals(action)) {
            req.getRequestDispatcher("/add-movie.jsp").forward(req, resp);
        } else {
            List<Movie> movies = movieService.listMovies();
            req.setAttribute("movies", movies);
            req.getRequestDispatcher("/admin-dashboard.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user.getRole() != Role.ADMIN) {
            resp.sendRedirect("/user/dashboard");
            return;
        }

        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            Long movieId = Long.parseLong(req.getParameter("id"));
            movieService.deleteMovie(movieId);
            resp.sendRedirect("/admin/dashboard");

        } else if ("add".equals(action)) {
            Movie movie = new Movie();
            movie.setTitle(req.getParameter("title"));
            movie.setDescription(req.getParameter("description"));
            movie.setReleaseDate(req.getParameter("releaseDate"));
            movie.setGenre(req.getParameter("genre"));
            movie.setRating(Integer.parseInt(req.getParameter("rating")));

            Part posterPart = req.getPart("poster");
            if (posterPart != null && posterPart.getSize() > 0) {
                byte[] posterBytes = new byte[(int) posterPart.getSize()];
                try (InputStream is = posterPart.getInputStream()) {
                    is.read(posterBytes);
                }
                String posterBase64 = Base64.getEncoder().encodeToString(posterBytes);
                movie.setPoster(posterBase64);
            }

            movieService.addMovie(movie);
            resp.sendRedirect("/admin/dashboard");

        } else if ("edit".equals(action)) {
            Movie movie = new Movie();
            movie.setId(Long.parseLong(req.getParameter("id")));
            movie.setTitle(req.getParameter("title"));
            movie.setDescription(req.getParameter("description"));
            movie.setReleaseDate(req.getParameter("releaseDate"));
            movie.setGenre(req.getParameter("genre"));
            movie.setRating(Integer.parseInt(req.getParameter("rating")));

            Part posterPart = req.getPart("poster");
            if (posterPart != null && posterPart.getSize() > 0) {
                byte[] posterBytes = new byte[(int) posterPart.getSize()];
                try (InputStream is = posterPart.getInputStream()) {
                    is.read(posterBytes);
                }
                String posterBase64 = Base64.getEncoder().encodeToString(posterBytes);
                movie.setPoster(posterBase64);
            }

            movieService.updateMovie(movie);
            resp.sendRedirect("/admin/dashboard");
        }
    }
}
