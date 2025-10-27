<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Profile</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2 class="mb-4">Profile</h2>
    <form method="POST" action="/user/profile">
        <div class="mb-3 row">
            <label class="col-sm-3 col-form-label" for="username">Username</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="username" name="username" value="<%= request.getAttribute("username") %>" required>
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-3 col-form-label" for="email">Email</label>
            <div class="col-sm-9">
                <input type="email" class="form-control" id="email" name="email" value="<%= request.getAttribute("email") %>" required>
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-3 col-form-label" for="newPassword">New Password</label>
            <div class="col-sm-9">
                <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="Leave empty to keep current password">
            </div>
        </div>
        <div class="mb-3 row">
            <label class="col-sm-3 col-form-label" for="currentPassword">Current Password</label>
            <div class="col-sm-9">
                <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Save Changes</button>
    </form>
</div>
</body>
</html>
