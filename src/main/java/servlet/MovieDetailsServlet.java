package servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.Movie;
import model.Comment;
import model.User;
import service.MovieService;
import service.impl.MovieServiceImpl;
import service.CommentService;
import service.impl.CommentServiceImpl;

import java.io.IOException;
import java.util.List;

public class MovieDetailsServlet extends HttpServlet {
    private final MovieService movieService = new MovieServiceImpl();
    private final CommentService commentService = new CommentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long movieId = Long.parseLong(req.getParameter("id"));
        Movie movie = movieService.getMovie(movieId);
        List<Comment> comments = commentService.getCommentsByMovie(movieId);
        double averageRating = commentService.getAverageRating(movieId);
        req.setAttribute("movie", movie);
        req.setAttribute("comments", comments);
        req.setAttribute("averageRating", averageRating);
        req.getRequestDispatcher("/movie-details.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long movieId = Long.parseLong(req.getParameter("movieId"));
        String commentText = req.getParameter("commentText");
        String ratingStr = req.getParameter("rating");
        Movie movie = movieService.getMovie(movieId);
        Comment comment = new Comment();
        comment.setText(commentText);
        comment.setMovie(movie);
        comment.setUser((User) req.getSession().getAttribute("user"));
        commentService.addComment(comment);
        if (ratingStr != null && !ratingStr.isEmpty()) {
            int rating = Integer.parseInt(ratingStr);
            comment.setRating(rating);
            commentService.updateComment(comment);
        }
        resp.sendRedirect("/movies/details?id=" + movieId);
    }
}
