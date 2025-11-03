<%@ page import="model.Movie" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Watchlist</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .poster { height: 180px; object-fit: cover; width: 100%; }
        .card-item { transition: transform .2s; }
        .card-item:hover { transform: translateY(-3px); }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
    <div class="container">
        <a class="navbar-brand" href="#">Watchlist</a>
        <div class="ms-auto d-flex gap-2">
            <a class="btn btn-outline-primary" href="/user/dashboard">Dashboard</a>
            <a class="btn btn-outline-secondary" href="/user/profile">Profile</a>
        </div>
    </div>
</nav>

<main class="container my-4">
    <div class="row g-4">
        <%
            List<Movie> watchlist = (List<Movie>) request.getAttribute("watchlist");
            if (watchlist != null) {
                for (Movie movie : watchlist) {
        %>
        <div class="col-6 col-sm-4 col-md-3">
            <div class="card card-item h-100">
                <img src="data:image/jpeg;base64,<%= movie.getPoster() %>" class="card-img-top poster" alt="Movie Poster">
                <div class="card-body d-flex flex-column">
                    <h6 class="card-title mb-1"><%= movie.getTitle() %></h6>
                    <p class="card-text mb-2 text-muted small"><%= movie.getReleaseDate() %> • <%= movie.getGenre() %> </p>
                    <form method="POST" action="/user/watchlist/action" class="mt-auto">
                        <input type="hidden" name="movieId" value="<%= movie.getId() %>">
                        <input type="hidden" name="type" value="remove">
                        <button class="btn btn-sm btn-outline-danger w-100" type="submit">Remove</button>
                    </form>
                </div>
            </div>
        </div>
        <%
                }
            }
        %>
    </div>
</main>
</body>
</html>
