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
        .comment-box { margin-top: 20px; }
        .star-rating {
            display: inline-block;
            direction: rtl;
            font-size: 30px;
            color: #ccc;
        }
        .star-rating input[type="radio"] {
            display: none;
        }
        .star-rating label {
            cursor: pointer;
        }
        .star-rating input[type="radio"]:checked ~ label {
            color: #ffd700;
        }
        .star-rating label:before {
            content: '\2605';
        }
        .star-rating input[type="radio"]:checked ~ label:before {
            content: '\2605';
        }
    </style>
</head>
<body>
<div class="container my-4">
    <a href="/user/dashboard" class="btn btn-outline-primary mb-3">Back to Dashboard</a>

    <%
        Movie movie = (Movie) request.getAttribute("movie");
        Double avgObj = (Double) request.getAttribute("averageRating");
        double averageRating = (avgObj != null) ? avgObj : 0.0;
        List<Comment> comments = (List<Comment>) request.getAttribute("comments");
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
            <p><strong>Average Rating:</strong> <%= String.format("%.1f", averageRating) %></p>
            <p><strong>Description:</strong> <%= movie.getDescription() %></p>
        </div>
    </div>

    <div class="comments-section mt-5">
        <h3>Comments</h3>
        <div class="comments-list">
            <%
                if (comments != null && !comments.isEmpty()) {
                    for (Comment comment : comments) {
            %>
            <div class="comment mb-3">
                <div class="card">
                    <div class="card-body">
                        <p><strong><%= comment.getUser() != null ? comment.getUser().getUsername() : "User" %>:</strong>
                            <%= comment.getText() %></p>
                        <p class="text-muted"><%= comment.getCreatedAt() %></p>
                        <p><strong>Rating:</strong> <%= comment.getRating() %> / 5</p>
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
        <form action="/movies/details" method="POST">
            <input type="hidden" name="movieId" value="<%= movie.getId() %>">
            <div class="form-group">
                <textarea class="form-control" name="commentText" rows="3" placeholder="Write your comment..." required></textarea>
            </div>
            <div class="form-group mt-2">
                <label for="rating">Rating (1-5):</label>
                <div class="star-rating">
                    <input type="radio" id="star5" name="rating" value="5"><label for="star5"></label>
                    <input type="radio" id="star4" name="rating" value="4"><label for="star4"></label>
                    <input type="radio" id="star3" name="rating" value="3"><label for="star3"></label>
                    <input type="radio" id="star2" name="rating" value="2"><label for="star2"></label>
                    <input type="radio" id="star1" name="rating" value="1"><label for="star1"></label>
                </div>
            </div>
            <button type="submit" class="btn btn-primary mt-3">Post Comment</button>
        </form>
    </div>

    <%
        }
    %>
</div>
</body>
</html>
