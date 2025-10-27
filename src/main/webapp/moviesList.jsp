<%@ page import="model.Movie" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Movies</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .poster { height: 260px; object-fit: cover; width: 100%; }
        .card-custom { transition: transform .2s; }
        .card-custom:hover { transform: translateY(-4px); }
        .filters .btn { margin-right: .25rem; }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light shadow-sm">
    <div class="container">
        <a class="navbar-brand" href="#">Movies</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbars" aria-controls="navbars" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbars">
            <form class="d-flex ms-auto" role="search">
                <input class="form-control me-2" type="search" placeholder="Search titles" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>

<main class="container my-4">
    <div class="row mb-3">
        <div class="col-12 filters">
            <span class="me-2 fw-semibold">Filters:</span>
            <button class="btn btn-sm btn-outline-primary" type="button">Action</button>
            <button class="btn btn-sm btn-outline-primary" type="button">Drama</button>
            <button class="btn btn-sm btn-outline-primary" type="button">Comedy</button>
            <button class="btn btn-sm btn-outline-secondary" type="button" id="clearFilters">Clear</button>
        </div>
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
                    <h6 class="card-title mb-1"><%= movie.getTitle() %></h6>
                    <p class="card-text mb-2 text-muted small"><%= movie.getReleaseDate() %> • <%= movie.getGenre() %> • <%= movie.getRating() %></p>
                    <div class="mt-auto d-flex justify-content-between align-items-center">
                        <form action="/movies/edit" method="POST" style="display:inline;">
                            <input type="hidden" name="id" value="<%= movie.getId() %>">
                            <button class="btn btn-sm btn-outline-secondary">Edit</button>
                        </form>
                        <form action="/movies/delete" method="POST" style="display:inline;">
                            <input type="hidden" name="id" value="<%= movie.getId() %>">
                            <button class="btn btn-sm btn-outline-danger">Delete</button>
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
</main>

</body>
</html>
