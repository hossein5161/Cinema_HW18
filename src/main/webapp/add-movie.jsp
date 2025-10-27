<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Add New Movie</title>
  <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
  <h2 class="mb-4">Add New Movie</h2>
  <form action="/movies" method="POST" enctype="multipart/form-data">
    <input type="hidden" name="action" value="add">

    <div class="mb-3">
      <label for="title" class="form-label">Title</label>
      <input type="text" class="form-control" id="title" name="title" required>
    </div>

    <div class="mb-3">
      <label for="description" class="form-label">Description</label>
      <textarea class="form-control" id="description" name="description" required></textarea>
    </div>

    <div class="mb-3">
      <label for="releaseDate" class="form-label">Release Date</label>
      <input type="date" class="form-control" id="releaseDate" name="releaseDate" required>
    </div>

    <div class="mb-3">
      <label for="genre" class="form-label">Genre</label>
      <input type="text" class="form-control" id="genre" name="genre" required>
    </div>

    <div class="mb-3">
      <label for="rating" class="form-label">Rating</label>
      <input type="number" class="form-control" id="rating" name="rating" required>
    </div>

    <div class="mb-3">
      <label for="poster" class="form-label">Poster</label>
      <input type="file" class="form-control" id="poster" name="poster" accept="image/*">
    </div>

    <button type="submit" class="btn btn-primary">Add Movie</button>
  </form>
</div>
</body>
</html>
