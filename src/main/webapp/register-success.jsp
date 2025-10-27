<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Registration Success</title>
  <link href="/css/bootstrap.min.css" rel="stylesheet">
  <style>
    .card-custom { max-width: 600px; margin: 2rem auto; padding: 1.5rem; }
  </style>
</head>
<body>
<div class="container">
  <div class="row justify-content-center">
    <div class="col-md-8 col-lg-6">
      <div class="card card-custom shadow-sm">
        <div class="card-body">
          <h2 class="card-title text-center mb-4">Registration Successful!</h2>
          <p class="text-center mb-4">You have been successfully registered as ${message}. Please proceed to login.</p>
          <div class="d-grid">
            <a href="/login" class="btn btn-primary btn-lg">Go to Login</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
