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

    <form action="/user/dashboard" method="GET" class="mb-4">
        <input type="text" name="search" class="form-control" placeholder="Search by title or genre" value="<%= request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : "" %>">
        <button type="submit" class="btn btn-primary mt-2">Search</button>
    </form>

    <div class="row g-4">
        <%
            List<Movie> movies = (List<Movie>) request.getAttribute("movies");
            if (movies != null && !movies.isEmpty()) {
                for (Movie movie : movies) {
        %>
        <div class="col-6 col-sm-4 col-md-3">
            <div class="card card-item h-100">
                <img src="data:image/jpeg;base64,<%= movie.getPoster() %>" class="card-img-top poster" alt="Movie Poster">
                <div class="card-body d-flex flex-column">
                    <h6 class="card-title mb-1"><%= movie.getTitle() %></h6>
                    <p class="card-text mb-2 text-muted small"><%= movie.getReleaseDate() %> • <%= movie.getGenre() %> </p>
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
        } else {
        %>
        <p>No movies found based on your search.</p>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
