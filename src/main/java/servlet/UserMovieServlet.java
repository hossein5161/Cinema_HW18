package servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;
import service.WatchlistService;
import service.impl.WatchlistServiceImpl;
import util.HtmlUtil;
import java.io.IOException;
import java.util.List;


public class UserMovieServlet extends HttpServlet {
    WatchlistService watchlistService = new WatchlistServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdStr = request.getParameter("userId");

        if (userIdStr == null || userIdStr.isEmpty()) {
            displayUserIdForm(response);
            return;
        }
        String path = request.getPathInfo();
        if (path !=null && path.equals("/view")) {
            doGetView(request,response);
        }
        displayOptions(response, userIdStr);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userIdStr = request.getParameter("userId");
        String movieIdStr = request.getParameter("movieId");
        String path = request.getPathInfo();
        if (path !=null && path.equals("/remove")) {
            doPostRemove(request,response);
            return;
        }
        try {
            Long userId = Long.valueOf(userIdStr);
            Long movieId = Long.valueOf(movieIdStr);
            watchlistService.addMovieToUser(userId, movieId);
            HtmlUtil.writeHtml(response, "Watchlist updated", "<h2>Movie added to your watchlist!</h2>");
        } catch (NumberFormatException e) {
            HtmlUtil.writeHtml(response, "Error", "<h2>Invalid User ID or Movie ID format!</h2>");
        }
    }

    private void displayUserIdForm(HttpServletResponse response) throws IOException {
        String htmlInput = "<h2>Please enter your User ID</h2>"
                + "<form action=/user-movies method=GET>"
                + "<label for=userId>User ID:</label>"
                + "<input type=text id=userId name=userId required>"
                + "<input type=submit value=Submit>"
                + "</form>";
        HtmlUtil.writeHtml(response, "Enter User ID", htmlInput);
    }

    private void displayOptions(HttpServletResponse response, String userIdStr) throws IOException {
        String htmlForms = "<h2>Options for User ID: " + userIdStr + "</h2>"
                + "<form action=/user-movies/add  method=POST>"
                + "<label for=movieIdAdd>Add Movie ID:</label>"
                + "<input type=text id=movieIdAdd name=movieId required>"
                + "<input type=hidden name=userId value=" + userIdStr + ">"
                + "<input type=submit value='Add to Watchlist'>"
                + "</form>"
                + "<form action=/user-movies/view method=GET>"
                + "<input type=hidden name=userId value=" + userIdStr + ">"
                + "<input type=submit value='View Watchlist'>"
                + "</form>"
                + "<form action=/user-movies/remove method=POST>"
                + "<label for=movieIdRemove>Remove Movie ID:</label>"
                + "<input type=text id=movieIdRemove name=movieId required>"
                + "<input type=hidden name=userId value=" + userIdStr + ">"
                + "<input type=submit value='Remove from Watchlist'>"
                + "</form>";
        HtmlUtil.writeHtml(response, "Movie Watchlist Options", htmlForms);


    }

    protected void doGetView(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userIdStr = request.getParameter("userId");
        try {
            Long userId = Long.valueOf(userIdStr);
            List<Movie> watchlist = watchlistService.getWatchlist(userId);
            StringBuilder html = new StringBuilder();
            html.append("<h2>Watchlist for User ").append(userId).append("</h2>");

            if (watchlist.isEmpty()) {
                html.append("<h2>Watchlist is empty!</h2>");
            } else {
                html.append("<ul>");
                for (Movie movie : watchlist) {
                    html.append("<li>")
                            .append(movie.getTitle())
                            .append(" (").append(movie.getGenre())
                            .append(", ").append(movie.getDuration())
                            .append(" min)</li>");
                }
                html.append("</ul>");
            }
            HtmlUtil.writeHtml(response, "Your Watchlist", html.toString());

        } catch (NumberFormatException e) {
            HtmlUtil.writeHtml(response, "Error", "<h2>Invalid User ID format!</h2>");
        }
    }





    protected void doPostRemove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userIdStr = request.getParameter("userId");
        String movieIdStr = request.getParameter("movieId");

        try {
            Long userId = Long.valueOf(userIdStr);
            Long movieId = Long.valueOf(movieIdStr);
            watchlistService.removeFromWatchlist(userId, movieId);
            HtmlUtil.writeHtml(response, "Watchlist updated", "<h2>Movie removed from your watchlist!</h2>");
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid User ID or Movie ID format!");
        }
    }

}
