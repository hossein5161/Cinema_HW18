<%@ page import="model.Movie" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>User Dashboard</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .poster { height: 260px; object-fit: cover; width: 100%; }
        .card-item { transition: transform .2s; }
        .card-item:hover { transform: translateY(-3px); }
    </style>
</head>
<body>
<div class="container">
    <h2 class="mb-4">Movies List</h2>

    <nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
        <div class="container">
            <a class="navbar-brand" href="/user/dashboard">Dashboard</a>
            <div class="ms-auto d-flex gap-2">
                <a class="btn btn-outline-primary" href="/user/watchlist">Watchlist</a>
                <a class="btn btn-outline-secondary" href="/user/profile">Profile</a>
                <form action="/logout" method="POST" style="display:inline;">
                    <button class="btn btn-outline-danger">Logout</button>
                </form>
            </div>
        </div>
    </nav>

    <div class="row g-4">
        <%
            List<Movie> movies = (List<Movie>) request.getAttribute("movies");
            if (movies != null) {
                for (Movie movie : movies) {
        %>
        <div class="col-6 col-sm-4 col-md-3">
            <div class="card card-item h-100">
                <img src="data:image/jpeg;base64,<%= movie.getPoster() %>" class="card-img-top poster" alt="Movie Poster">
                <div class="card-body d-flex flex-column">
                    <h6 class="card-title mb-1"><%= movie.getTitle() %></h6>
                    <p class="card-text mb-2 text-muted small"><%= movie.getReleaseDate() %> • <%= movie.getGenre() %> • <%= movie.getRating() %></p>
                    <form method="POST" action="/user/watchlist/action" class="mt-auto">
                        <input type="hidden" name="movieId" value="<%= movie.getId() %>">
                        <input type="hidden" name="type" value="add">
                        <button class="btn btn-sm btn-outline-primary w-100" type="submit">Add to Watchlist</button>
                    </form>
                    <a href="/user/movie/details?id=<%= movie.getId() %>" class="btn btn-sm btn-outline-secondary w-100 mt-2">Details</a>
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
