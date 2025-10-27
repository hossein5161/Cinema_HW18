<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Movie" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Edit Movie</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2 class="mb-4">Edit Movie</h2>
    <form action="/movies" method="POST" enctype="multipart/form-data">
        <input type="hidden" name="action" value="edit">
        <%
            Movie movie = (Movie) request.getAttribute("movie");
        %>
        <input type="hidden" name="id" value="<%= movie.getId() %>">

        <div class="mb-3">
            <label for="title" class="form-label">Title</label>
            <input type="text" class="form-control" id="title" name="title" value="<%= movie.getTitle() %>" required>
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <textarea class="form-control" id="description" name="description" required><%= movie.getDescription() %></textarea>
        </div>

        <div class="mb-3">
            <label for="releaseDate" class="form-label">Release Date</label>
            <input type="date" class="form-control" id="releaseDate" name="releaseDate" value="<%= movie.getReleaseDate() %>" required>
        </div>

        <div class="mb-3">
            <label for="genre" class="form-label">Genre</label>
            <input type="text" class="form-control" id="genre" name="genre" value="<%= movie.getGenre() %>" required>
        </div>

        <div class="mb-3">
            <label for="rating" class="form-label">Rating</label>
            <input type="number" class="form-control" id="rating" name="rating" value="<%= movie.getRating() %>" required>
        </div>

        <div class="mb-3">
            <label for="poster" class="form-label">Poster</label>
            <input type="file" class="form-control" id="poster" name="poster" accept="image/*">
        </div>

        <button type="submit" class="btn btn-primary">Update Movie</button>
    </form>
</div>
</body>
</html>
