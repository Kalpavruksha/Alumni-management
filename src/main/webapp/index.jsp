<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Alumni Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container { margin-top: 30px; }
        .form-container { max-width: 800px; margin: 0 auto; }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="text-center mb-4">Alumni Management System</h1>
        
        <div class="form-container">
            <form action="alumni" method="post" class="mb-4">
                <input type="hidden" name="action" value="add">
                
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="name" class="form-label">Name</label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="graduationYear" class="form-label">Graduation Year</label>
                        <input type="text" class="form-control" id="graduationYear" name="graduationYear">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="course" class="form-label">Course</label>
                        <input type="text" class="form-control" id="course" name="course">
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="currentPosition" class="form-label">Current Position</label>
                        <input type="text" class="form-control" id="currentPosition" name="currentPosition">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="company" class="form-label">Company</label>
                        <input type="text" class="form-control" id="company" name="company">
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="phoneNumber" class="form-label">Phone Number</label>
                        <input type="text" class="form-control" id="phoneNumber" name="phoneNumber">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="address" class="form-label">Address</label>
                        <input type="text" class="form-control" id="address" name="address">
                    </div>
                </div>
                
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Add Alumni</button>
                </div>
            </form>
            
            <h2 class="mb-3">Alumni List</h2>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Graduation Year</th>
                            <th>Course</th>
                            <th>Position</th>
                            <th>Company</th>
                            <th>Phone</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${alumniList}" var="alumni">
                            <tr>
                                <td>${alumni.name}</td>
                                <td>${alumni.email}</td>
                                <td>${alumni.graduationYear}</td>
                                <td>${alumni.course}</td>
                                <td>${alumni.currentPosition}</td>
                                <td>${alumni.company}</td>
                                <td>${alumni.phoneNumber}</td>
                                <td>
                                    <form action="alumni" method="post" style="display: inline;">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="${alumni.id}">
                                        <button type="submit" class="btn btn-danger btn-sm" 
                                                onclick="return confirm('Are you sure you want to delete this alumni?')">
                                            Delete
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 