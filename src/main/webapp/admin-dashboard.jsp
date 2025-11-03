<%@ page import="model.Movie" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Admin Dashboard</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .poster { height: 260px; object-fit: cover; width: 100%; }
        .card-custom { transition: transform .2s; }
        .card-custom:hover { transform: translateY(-4px); }
    </style>
</head>
<body>
<div class="container">
    <h2 class="mb-4">Movies List</h2>

    <form action="/logout" method="POST" style="display: inline;">
        <button type="submit" class="btn btn-outline-danger btn-sm mb-3">Logout</button>
    </form>

    <div class="mb-3">
        <a href="/movies?action=add" class="btn btn-primary">Add New Movie</a>
    </div>

    <div class="row g-4" id="moviesGrid">
        <%
            List<Movie> movies = (List<Movie>) request.getAttribute("movies");
            if (movies != null) {
                for (Movie movie : movies) {
        %>
        <div class="col-6 col-sm-4 col-md-3">
            <div class="card card-custom h-100">
                <img src="data:image/jpeg;base64,<%= movie.getPoster() %>" class="card-img-top poster" alt="Movie Poster">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title mb-1"><%= movie.getTitle() %></h5>
                    <p class="card-text text-muted small"><%= movie.getReleaseDate() %> • <%= movie.getGenre() %> </p>
                    <div class="d-flex justify-content-between">

                        <a href="/movies?action=edit&id=<%= movie.getId() %>" class="btn btn-outline-secondary btn-sm">Edit</a>

                        <form action="/movies" method="POST" style="display:inline;">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="<%= movie.getId() %>">
                            <button type="submit" class="btn btn-outline-danger btn-sm">Delete</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%
                }
            }
        %>
    </div>
</div>
</body>
</html>
