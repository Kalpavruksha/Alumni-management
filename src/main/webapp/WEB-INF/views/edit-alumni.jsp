<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Alumni - Alumni Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container { margin-top: 30px; }
        .form-group { margin-bottom: 15px; }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="mb-4">Edit Alumni</h1>
        
        <div class="card">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/alumni" method="post" class="needs-validation" novalidate>
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="${alumni.id}">
                    
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="name">Name:</label>
                                <input type="text" class="form-control" id="name" name="name" value="${alumni.name}" required>
                                <div class="invalid-feedback">Please enter a name.</div>
                            </div>
                            <div class="form-group">
                                <label for="email">Email:</label>
                                <input type="email" class="form-control" id="email" name="email" value="${alumni.email}" required>
                                <div class="invalid-feedback">Please enter a valid email address.</div>
                            </div>
                            <div class="form-group">
                                <label for="graduationYear">Graduation Year:</label>
                                <input type="text" class="form-control" id="graduationYear" name="graduationYear" value="${alumni.graduationYear}" pattern="\d{4}" required>
                                <div class="invalid-feedback">Please enter a valid year (YYYY).</div>
                            </div>
                            <div class="form-group">
                                <label for="course">Course:</label>
                                <input type="text" class="form-control" id="course" name="course" value="${alumni.course}" required>
                                <div class="invalid-feedback">Please enter a course.</div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label for="currentPosition">Current Position:</label>
                                <input type="text" class="form-control" id="currentPosition" name="currentPosition" value="${alumni.currentPosition}" required>
                                <div class="invalid-feedback">Please enter a current position.</div>
                            </div>
                            <div class="form-group">
                                <label for="company">Company:</label>
                                <input type="text" class="form-control" id="company" name="company" value="${alumni.company}" required>
                                <div class="invalid-feedback">Please enter a company name.</div>
                            </div>
                            <div class="form-group">
                                <label for="phoneNumber">Phone Number:</label>
                                <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" value="${alumni.phoneNumber}" pattern="[0-9]{10}" required>
                                <div class="invalid-feedback">Please enter a valid 10-digit phone number.</div>
                            </div>
                            <div class="form-group">
                                <label for="address">Address:</label>
                                <textarea class="form-control" id="address" name="address" rows="3" required>${alumni.address}</textarea>
                                <div class="invalid-feedback">Please enter an address.</div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="mt-3">
                        <button type="submit" class="btn btn-primary">Update Alumni</button>
                        <a href="${pageContext.request.contextPath}/alumni" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
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
    </script>
</body>
</html> 