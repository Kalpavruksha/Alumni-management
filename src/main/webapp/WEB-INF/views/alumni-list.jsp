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
        .table th { cursor: pointer; }
        .table th:hover { background-color: #f8f9fa; }
        .pagination { margin-top: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="mb-4">Alumni Management System</h1>
        
        <!-- Alert Messages -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        
        <!-- Search and Add Form -->
        <div class="row mb-4">
            <div class="col-md-8">
                <form action="${pageContext.request.contextPath}/alumni" method="get" class="d-flex">
                    <input type="hidden" name="action" value="list">
                    <input type="text" name="search" class="form-control me-2" placeholder="Search alumni..." value="${param.search}">
                    <button type="submit" class="btn btn-primary">Search</button>
                </form>
            </div>
            <div class="col-md-4 text-end">
                <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addAlumniModal">
                    Add New Alumni
                </button>
            </div>
        </div>
        
        <!-- Alumni List -->
        <div class="card">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th onclick="sortTable('id')">ID ${param.sortBy == 'id' ? (param.sortOrder == 'asc' ? '↑' : '↓') : ''}</th>
                                <th onclick="sortTable('name')">Name ${param.sortBy == 'name' ? (param.sortOrder == 'asc' ? '↑' : '↓') : ''}</th>
                                <th onclick="sortTable('email')">Email ${param.sortBy == 'email' ? (param.sortOrder == 'asc' ? '↑' : '↓') : ''}</th>
                                <th onclick="sortTable('graduationYear')">Graduation Year ${param.sortBy == 'graduationYear' ? (param.sortOrder == 'asc' ? '↑' : '↓') : ''}</th>
                                <th onclick="sortTable('course')">Course ${param.sortBy == 'course' ? (param.sortOrder == 'asc' ? '↑' : '↓') : ''}</th>
                                <th onclick="sortTable('currentPosition')">Current Position ${param.sortBy == 'currentPosition' ? (param.sortOrder == 'asc' ? '↑' : '↓') : ''}</th>
                                <th onclick="sortTable('company')">Company ${param.sortBy == 'company' ? (param.sortOrder == 'asc' ? '↑' : '↓') : ''}</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${alumniList}" var="alumni">
                                <tr>
                                    <td>${alumni.id}</td>
                                    <td>${alumni.name}</td>
                                    <td>${alumni.email}</td>
                                    <td>${alumni.graduationYear}</td>
                                    <td>${alumni.course}</td>
                                    <td>${alumni.currentPosition}</td>
                                    <td>${alumni.company}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/alumni?action=edit&id=${alumni.id}" class="btn btn-sm btn-primary">Edit</a>
                                        <button onclick="deleteAlumni(${alumni.id})" class="btn btn-sm btn-danger">Delete</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                
                <!-- Pagination -->
                <c:if test="${totalPages > 1}">
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/alumni?action=list&page=${currentPage - 1}&search=${param.search}&sortBy=${param.sortBy}&sortOrder=${param.sortOrder}">Previous</a>
                            </li>
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${currentPage == i ? 'active' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/alumni?action=list&page=${i}&search=${param.search}&sortBy=${param.sortBy}&sortOrder=${param.sortOrder}">${i}</a>
                                </li>
                            </c:forEach>
                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/alumni?action=list&page=${currentPage + 1}&search=${param.search}&sortBy=${param.sortBy}&sortOrder=${param.sortOrder}">Next</a>
                            </li>
                        </ul>
                    </nav>
                </c:if>
            </div>
        </div>
    </div>
    
    <!-- Add Alumni Modal -->
    <div class="modal fade" id="addAlumniModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Add New Alumni</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/alumni" method="post" class="needs-validation" novalidate>
                        <input type="hidden" name="action" value="add">
                        
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="name">Name:</label>
                                    <input type="text" class="form-control" id="name" name="name" required>
                                    <div class="invalid-feedback">Please enter a name.</div>
                                </div>
                                <div class="form-group">
                                    <label for="email">Email:</label>
                                    <input type="email" class="form-control" id="email" name="email" required>
                                    <div class="invalid-feedback">Please enter a valid email address.</div>
                                </div>
                                <div class="form-group">
                                    <label for="graduationYear">Graduation Year:</label>
                                    <input type="text" class="form-control" id="graduationYear" name="graduationYear" pattern="\d{4}" required>
                                    <div class="invalid-feedback">Please enter a valid year (YYYY).</div>
                                </div>
                                <div class="form-group">
                                    <label for="course">Course:</label>
                                    <input type="text" class="form-control" id="course" name="course" required>
                                    <div class="invalid-feedback">Please enter a course.</div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="currentPosition">Current Position:</label>
                                    <input type="text" class="form-control" id="currentPosition" name="currentPosition" required>
                                    <div class="invalid-feedback">Please enter a current position.</div>
                                </div>
                                <div class="form-group">
                                    <label for="company">Company:</label>
                                    <input type="text" class="form-control" id="company" name="company" required>
                                    <div class="invalid-feedback">Please enter a company name.</div>
                                </div>
                                <div class="form-group">
                                    <label for="phoneNumber">Phone Number:</label>
                                    <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" pattern="[0-9]{10}" required>
                                    <div class="invalid-feedback">Please enter a valid 10-digit phone number.</div>
                                </div>
                                <div class="form-group">
                                    <label for="address">Address:</label>
                                    <textarea class="form-control" id="address" name="address" rows="3" required></textarea>
                                    <div class="invalid-feedback">Please enter an address.</div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="mt-3">
                            <button type="submit" class="btn btn-primary">Add Alumni</button>
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Form validation
        (function () {
            'use strict'
            var forms = document.querySelectorAll('.needs-validation')
            Array.prototype.slice.call(forms).forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    form.classList.add('was-validated')
                }, false)
            })
        })()
        
        // Sort table
        function sortTable(column) {
            const currentSortBy = '${param.sortBy}';
            const currentSortOrder = '${param.sortOrder}';
            const newSortOrder = currentSortBy === column && currentSortOrder === 'asc' ? 'desc' : 'asc';
            
            window.location.href = '${pageContext.request.contextPath}/alumni?action=list&sortBy=' + column + '&sortOrder=' + newSortOrder + '&search=${param.search}&page=${currentPage}';
        }
        
        // Delete alumni
        function deleteAlumni(id) {
            if (confirm('Are you sure you want to delete this alumni record?')) {
                window.location.href = '${pageContext.request.contextPath}/alumni?action=delete&id=' + id;
            }
        }
    </script>
</body>
</html> 