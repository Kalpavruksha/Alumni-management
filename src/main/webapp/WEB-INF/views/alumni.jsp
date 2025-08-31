<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Alumni Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container { margin-top: 30px; }
        .form-group { margin-bottom: 15px; }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="mb-4">Alumni Management System</h1>
        
        <!-- Add Alumni Form -->
        <div class="card mb-4">
            <div class="card-header">
                <h3>Add New Alumni</h3>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/alumni" method="post">
                    <input type="hidden" name="action" value="add">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="name">Name:</label>
                                <input type="text" class="form-control" id="name" name="name" required>
                            </div>
                            <div class="form-group">
                                <label for="email">Email:</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                            </div>
                            <div class="form-group">
                                <label for="graduationYear">Graduation Year:</label>
                                <input type="text" class="form-control" id="graduationYear" name="graduationYear">
                            </div>
                            <div class="form-group">
                                <label for="course">Course:</label>
                                <input type="text" class="form-control" id="course" name="course">
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="currentPosition">Current Position:</label>
                                <input type="text" class="form-control" id="currentPosition" name="currentPosition">
                            </div>
                            <div class="form-group">
                                <label for="company">Company:</label>
                                <input type="text" class="form-control" id="company" name="company">
                            </div>
                            <div class="form-group">
                                <label for="phoneNumber">Phone Number:</label>
                                <input type="text" class="form-control" id="phoneNumber" name="phoneNumber">
                            </div>
                            <div class="form-group">
                                <label for="address">Address:</label>
                                <textarea class="form-control" id="address" name="address" rows="3"></textarea>
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Add Alumni</button>
                </form>
            </div>
        </div>
        
        <!-- Alumni List -->
        <div class="card">
            <div class="card-header">
                <h3>Alumni List</h3>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Graduation Year</th>
                                <th>Course</th>
                                <th>Current Position</th>
                                <th>Company</th>
                                <th>Phone</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="alumni" items="${alumniList}">
                                <tr>
                                    <td>${alumni.id}</td>
                                    <td>${alumni.name}</td>
                                    <td>${alumni.email}</td>
                                    <td>${alumni.graduationYear}</td>
                                    <td>${alumni.course}</td>
                                    <td>${alumni.currentPosition}</td>
                                    <td>${alumni.company}</td>
                                    <td>${alumni.phoneNumber}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/alumni?action=delete&id=${alumni.id}" 
                                           class="btn btn-danger btn-sm"
                                           onclick="return confirm('Are you sure you want to delete this alumni?')">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 