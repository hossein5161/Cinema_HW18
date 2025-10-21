package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import model.Movie;
import model.Role;
import model.User;
import service.MovieService;
import service.impl.MovieServiceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

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
        String requestURI = req.getRequestURI();
        if (requestURI != null && requestURI.equals("/movies/edit")) {
            Long movieId = (Long) req.getSession().getAttribute("movieId");
            Movie movie = movieService.getMovie(movieId);
            if (movie == null) {
                resp.sendRedirect("/admin/dashboard");
                return;
            }
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'><title>Edit Movie</title>");
            out.println("<link href='/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("<style>.card-custom{max-width:700px;margin:2rem auto;padding:1.5rem}.poster-preview{height:200px;object-fit:cover;width:100%;border:1px solid #ccc;border-radius:4px}</style>");
            out.println("</head><body>");
            out.println("<div class='container'><div class='card card-custom shadow-sm'><div class='card-body'>");
            out.println("<h3 class='text-center mb-4'>Edit Movie</h3>");
            out.println("<form method='POST' action='/movies/edit' enctype='multipart/form-data'>");
            out.println("<input type='hidden' name='id' value='" + movie.getId() + "'>");
            out.println("<div class='mb-3'><label class='form-label'>Title</label>");
            out.println("<input type='text' name='title' class='form-control' value='" + movie.getTitle() + "' required></div>");
            out.println("<div class='mb-3'><label class='form-label'>Description</label>");
            out.println("<textarea name='description' class='form-control' rows='3'>" + movie.getDescription() + "</textarea></div>");
            out.println("<div class='mb-3'><label class='form-label'>Genre</label>");
            out.println("<input type='text' name='genre' class='form-control' value='" + movie.getGenre() + "'></div>");
            out.println("<div class='mb-3'><label class='form-label'>Release Date</label>");
            out.println("<input type='date' name='releaseDate' class='form-control' value='" + movie.getReleaseDate() + "'></div>");
            out.println("<div class='mb-3'><label class='form-label'>Rating</label>");
            out.println("<input type='number' name='rating' min='0' max='10' class='form-control' value='" + movie.getRating() + "'></div>");
            out.println("<div class='mb-3'><label class='form-label'>Poster</label>");
            out.println("<input type='file' name='poster' class='form-control' accept='image/*'>");
            String posterBase64 = movie.getPoster() != null ? movie.getPoster() : "";
            out.println("<div class='mt-2'><img src='data:image/jpeg;base64," + posterBase64 + "' alt='Poster Preview' class='poster-preview'></div>");
            out.println("</div>");
            out.println("<div class='d-flex justify-content-between'><button type='submit' class='btn btn-primary'>Save Changes</button>");
            out.println("<a href='/admin/dashboard' class='btn btn-secondary'>Cancel</a></div>");
            out.println("</form></div></div></div></body></html>");
            out.close();
            return;
        }



        List<Movie> movies = movieService.listMovies();

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'><head><meta charset='UTF-8'><title>Admin Dashboard - Movies</title>");
        out.println("<link href='/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<style>.poster{height:260px;object-fit:cover;width:100%;}.card-item{transition:transform .2s;}.card-item:hover{transform:translateY(-3px);}</style>");
        out.println("</head><body>");
        out.println("<nav class='navbar navbar-expand-lg navbar-dark bg-dark'><div class='container'>");
        out.println("<a class='navbar-brand' href='#'>Admin Panel</a>");
        out.println("<div class='ms-auto text-white d-flex align-items-center gap-2'>");
        out.println("<span id='adminUser' class='fw-semibold'>Admin</span>");
        out.println("<form action='/logout' method='POST' class='d-inline'><button type='submit' class='btn btn-sm btn-outline-light'>Logout</button></form>");
        out.println("</div></div></nav>");
        out.println("<main class='container my-4'>");
        out.println("<div class='d-flex justify-content-between align-items-center mb-3'>");
        out.println("<h2 class='mb-0'>Movies</h2>");
        out.println("<button class='btn btn-primary' type='button' onclick=window.location.href='/pages/addMovie.html'>Add New Movie</button>");
        out.println("</div>");
        out.println("<div class='row g-4' id='moviesGrid'>");

        for (Movie movie : movies) {
            out.println("<div class='col-6 col-sm-4 col-md-3'>");
            out.println("<div class='card card-item h-100'>");
            String poster = movie.getPoster() != null ? "data:image/jpeg;base64," + movie.getPoster() : "https://via.placeholder.com/300x450?text=Poster";
            out.println("<img src='" + poster + "' class='card-img-top poster' alt='Poster'>");
            out.println("<div class='card-body d-flex flex-column'>");
            out.println("<h6 class='card-title mb-1'>" + movie.getTitle() + "</h6>");
            out.println("<p class='card-text mb-2 text-muted small'>" + movie.getReleaseDate() + " • " + movie.getGenre() + " • " + movie.getRating() + "</p>");
            out.println("<div class='mt-auto d-flex justify-content-between align-items-center'>");
            out.println("<form action='/movies/edit' method='POST' style='display:inline;'><input type='hidden' name='id' value='" + movie.getId() + "'><button class='btn btn-sm btn-outline-secondary'>Edit</button></form>");
            out.println("<form action='/movies/delete' method='POST' style='display:inline;'><input type='hidden' name='id' value='" + movie.getId() + "'><button class='btn btn-sm btn-outline-danger'>Delete</button></form>");
            out.println("</div></div></div></div>");
        }
        out.println("</div></main></body></html>");
        out.close();
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

        Movie movie = new Movie();
        String action = null;
        if (req.getContentType() != null && req.getContentType().toLowerCase().startsWith("multipart/")) {

            for (Part part : req.getParts()) {
                String partName = part.getName();
                if (part.getContentType() == null) {
                    String value = getPartAsString(part);
                    switch (partName) {
                        case "id":
                            movie.setId(Long.valueOf(value));
                            break;
                        case "action":
                            action = value;
                            break;
                        case "title":
                            movie.setTitle(value);
                            break;
                        case "description":
                            movie.setDescription(value);
                            break;
                        case "genre":
                            movie.setGenre(value);
                            break;
                        case "releaseDate":
                            movie.setReleaseDate(value);
                            break;
                        case "rating":
                            movie.setRating(value != null && !value.isEmpty() ? Integer.parseInt(value) : 0);
                            break;

                    }
                    if (action == null) {
                        action = "/update";
                    }
                } else if (partName.equals("poster") && part.getSize() > 0) {
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                byte[] buffer = new byte[1024];
                                try (InputStream is = part.getInputStream()) {
                                    int bytesRead;
                                    while ((bytesRead = is.read(buffer)) != -1) {
                                        bos.write(buffer, 0, bytesRead);
                                    }
                    }
                    movie.setPoster(Base64.getEncoder().encodeToString(bos.toByteArray()));
                }
            }
        }else {
            if ("/edit".equals(req.getPathInfo())) {
                action = "/movies/edit";
            } else if ("/delete".equals(req.getPathInfo())) {
                action = "/movies/delete";
            }
        }

        processAction(action, movie, req, resp);
    }

    private void processAction(String action, Movie movie, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        switch (action) {
            case "/movies/add":
                movieService.addMovie(movie);
                req.setAttribute("message", "Film added successfully");
                resp.sendRedirect("/admin/dashboard");
                break;
            case "/movies/edit":
                Long movieId = Long.parseLong(req.getParameter("id"));
                HttpSession session = req.getSession();
                session.setAttribute("movieId",movieId);
//                movieService.updateMovie(movie);
                resp.sendRedirect("/movies/edit");
                break;
            case "/update":
                movieService.updateMovie(movie);
                resp.sendRedirect("/admin/dashboard");
                break;
            case "/movies/delete":
                Long id = Long.parseLong(req.getParameter("id"));
                movieService.deleteMovie(id);
                resp.sendRedirect("/admin/dashboard");
                break;
            default:
                req.setAttribute("error", "Invalid action");
                req.getRequestDispatcher("/pages/admin-dashboard.html").forward(req, resp);
                break;
        }
    }

    private String getPartAsString(Part part) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        try (InputStream is = part.getInputStream()) {
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
        }
        return baos.toString("UTF-8");
    }
}
