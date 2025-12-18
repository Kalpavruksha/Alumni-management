package com.alumni.model;

public class Alumni {
    private int id;
    private String name;
    private String email;
    private String graduationYear;
    private String course;
    private String currentPosition;
    private String company;
    private String phoneNumber;
    private String address;

    // Default constructor
    public Alumni() {}

    // Constructor with all fields
    public Alumni(int id, String name, String email, String graduationYear, 
                 String course, String currentPosition, String company, 
                 String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.graduationYear = graduationYear;
        this.course = course;
        this.currentPosition = currentPosition;
        this.company = company;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
} // improve model validation
// improve model validation
// add getters and setters
// override toString method
