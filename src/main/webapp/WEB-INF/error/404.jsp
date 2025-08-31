<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Page Not Found</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            text-align: center;
        }
        .error-container {
            max-width: 500px;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1 class="mb-4">404 - Page Not Found</h1>
        <p class="lead">The page you are looking for might have been removed, had its name changed, or is temporarily unavailable.</p>
        <a href="${pageContext.request.contextPath}/alumni" class="btn btn-primary mt-3">Go to Homepage</a>
    </div>
</body>
</html> 