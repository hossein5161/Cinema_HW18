package servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;
import service.MovieService;
import service.impl.MovieServiceImpl;
import util.HtmlUtil;

import java.io.IOException;


public class MovieServlet extends HttpServlet {
    MovieService movieService = new MovieServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String htmlForm = "<form method=POST action=movies>"
                + "Title: <input type=text name=title required/><br/>"
                + "Genre: <input type=text name=genre required/><br/>"
                + "Duration: <input type=number name=duration required/><br/>"
                + "<input type=submit value='Add Movie'/>"
                + "</form>";
        HtmlUtil.writeHtml(resp, "Add Movie", htmlForm);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title = req.getParameter("title");
        String genre = req.getParameter("genre");
        String durationStr = req.getParameter("duration");
        int duration;

        try {
            duration = Integer.parseInt(durationStr);
        } catch (NumberFormatException e) {
            HtmlUtil.writeHtml(resp, "Input Error", "<h2>Invalid duration entered!</h2>");
            return;
        }

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setDuration(duration);
        movieService.addMovie(movie);

        HtmlUtil.writeHtml(resp, "Movie Added", "<h2>Movie added successfully!</h2>");
    }
}

