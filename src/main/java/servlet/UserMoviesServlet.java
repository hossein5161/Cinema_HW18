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
import java.io.PrintWriter;
import java.util.List;


public class UserMoviesServlet extends HttpServlet {

    private final MovieService movieService = new MovieServiceImpl();
    private final WatchlistService watchlistService = new WatchlistServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || "/dashboard".equals(pathInfo)) {
            List<Movie> movies = movieService.listMovies();
            renderDashboard(resp, movies);
        } else if ("/watchlist".equals(pathInfo)) {
            List<Movie> watchlist = watchlistService.getWatchlist(user);
            renderWatchlist(resp, watchlist);
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
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

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

    private void renderDashboard(HttpServletResponse resp, List<Movie> movies) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'><title>User Dashboard</title>");
        out.println("<link href='/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<style>.poster{height:260px;object-fit:cover;width:100%;}.card-item{transition:transform .2s;}.card-item:hover{transform:translateY(-3px);}</style>");
        out.println("</head><body>");
        out.println("<nav class='navbar navbar-expand-lg navbar-light bg-light shadow-sm'><div class='container'>");
        out.println("<a class='navbar-brand' href='/user/dashboard'>Dashboard</a>");
        out.println("<div class='ms-auto d-flex gap-2'>");
        out.println("<a class='btn btn-outline-primary' href='/user/watchlist'>Watchlist</a>");
        out.println("<a class='btn btn-outline-secondary' href='/profile'>Profile</a>");
        out.println("</div></div></nav>");
        out.println("<main class='container my-4'><div class='row g-4'>");
        for (Movie movie : movies) {
            String poster = movie.getPoster() != null ? "data:image/jpeg;base64," + movie.getPoster() : "https://via.placeholder.com/300x420?text=Poster";
            out.println("<div class='col-6 col-sm-4 col-md-3'>");
            out.println("<div class='card card-item h-100'>");
            out.println("<img src='" + poster + "' class='card-img-top poster' alt='Poster'>");
            out.println("<div class='card-body d-flex flex-column'>");
            out.println("<h6 class='card-title mb-1'>" + movie.getTitle() + "</h6>");
            out.println("<p class='card-text mb-2 text-muted small'>" + movie.getReleaseDate() + " • " + movie.getGenre() + " • " + movie.getRating() + "</p>");
            out.println("<form method='post' action='/user/watchlist/action' class='mt-auto'>");
            out.println("<input type='hidden' name='movieId' value='" + movie.getId() + "'>");
            out.println("<input type='hidden' name='type' value='add'>");
            out.println("<button class='btn btn-sm btn-outline-primary w-100' type='submit'>Add to Watchlist</button>");
            out.println("</form></div></div></div>");
        }
        out.println("</div></main></body></html>");
        out.close();
    }

    private void renderWatchlist(HttpServletResponse resp, List<Movie> watchlist) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'><title>Watchlist</title>");
        out.println("<link href='/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<style>.poster{height:180px;object-fit:cover;width:100%;}.card-item{transition:transform .2s;}.card-item:hover{transform:translateY(-3px);}</style>");
        out.println("</head><body>");
        out.println("<nav class='navbar navbar-expand-lg navbar-light bg-light shadow-sm'><div class='container'>");
        out.println("<a class='navbar-brand' href='/user/dashboard'>Dashboard</a>");
        out.println("<div class='ms-auto d-flex gap-2'><a class='btn btn-outline-secondary' href='/profile'>Profile</a></div>");
        out.println("</div></nav>");
        out.println("<main class='container my-4'><div class='row g-4'>");
        for (Movie movie : watchlist) {
            String poster = movie.getPoster() != null ? "data:image/jpeg;base64," + movie.getPoster() : "https://via.placeholder.com/300x420?text=Poster";
            out.println("<div class='col-6 col-sm-4 col-md-3'>");
            out.println("<div class='card card-item h-100'>");
            out.println("<img src='" + poster + "' class='card-img-top poster' alt='Poster'>");
            out.println("<div class='card-body d-flex flex-column'>");
            out.println("<h6 class='card-title mb-1'>" + movie.getTitle() + "</h6>");
            out.println("<p class='card-text mb-2 text-muted small'>" + movie.getReleaseDate() + " • " + movie.getGenre() + " • " + movie.getRating() + "</p>");
            out.println("<form method='post' action='/user/watchlist/action' class='mt-auto'>");
            out.println("<input type='hidden' name='movieId' value='" + movie.getId() + "'>");
            out.println("<input type='hidden' name='type' value='remove'>");
            out.println("<button class='btn btn-sm btn-outline-danger w-100' type='submit'>Remove</button>");
            out.println("</form></div></div></div>");
        }
        out.println("</div></main></body></html>");
        out.close();
    }
}
