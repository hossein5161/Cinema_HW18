<%@ page import="model.Movie, model.Comment" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Movie Details</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .poster { height: 260px; object-fit: cover; width: 100%; }
        .card-custom { transition: transform .2s; }
        .card-custom:hover { transform: translateY(-4px); }
        .comment-box { margin-top: 20px; }
    </style>
</head>
<body>
<div class="container my-4">
    <%
        Movie movie = (Movie) request.getAttribute("movie");
        if (movie != null) {
    %>
    <div class="row">
        <div class="col-md-4">
            <img src="data:image/jpeg;base64,<%= movie.getPoster() %>" class="card-img-top poster" alt="Movie Poster">
        </div>
        <div class="col-md-8">
            <h2><%= movie.getTitle() %></h2>
            <p><strong>Release Date:</strong> <%= movie.getReleaseDate() %></p>
            <p><strong>Genre:</strong> <%= movie.getGenre() %></p>
            <p><strong>Rating:</strong> <%= movie.getRating() %></p>
            <p><strong>Description:</strong> <%= movie.getDescription() %></p>
        </div>
    </div>

    <div class="comments-section mt-5">
        <h3>Comments</h3>
        <div class="comments-list">
            <%
                List<Comment> comments = (List<Comment>) request.getAttribute("comments");
                if (comments != null && !comments.isEmpty()) {
                    for (Comment comment : comments) {
            %>
            <div class="comment mb-3">
                <div class="card">
                    <div class="card-body">
                        <p><strong><%= comment.getUser().getUsername() %>:</strong> <%= comment.getText() %></p>
                        <p class="text-muted"><%= comment.getCreatedAt() %></p>
                    </div>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <p>No comments yet. Be the first to comment!</p>
            <%
                }
            %>
        </div>
    </div>

    <div class="comment-box">
        <h4>Add a Comment</h4>
        <form action="/movies/addComment" method="POST">
            <input type="hidden" name="movieId" value="<%= movie.getId() %>">
            <div class="form-group">
                <textarea class="form-control" name="commentText" rows="3" placeholder="Write your comment..." required></textarea>
            </div>
            <button type="submit" class="btn btn-primary mt-2">Post Comment</button>
        </form>
    </div>

    <%
        }
    %>
</div>
</body>
</html>
