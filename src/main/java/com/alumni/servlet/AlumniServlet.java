package com.alumni.servlet;

import com.alumni.dao.AlumniDAO;
import com.alumni.model.Alumni;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/alumni")
public class AlumniServlet extends HttpServlet {
    private AlumniDAO alumniDAO;
    private static final int PAGE_SIZE = 10;

    @Override
    public void init() throws ServletException {
        alumniDAO = new AlumniDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        try {
            switch (action) {
                case "list":
                    listAlumni(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteAlumni(request, response);
                    break;
                default:
                    listAlumni(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "add";
        }
        
        try {
            switch (action) {
                case "add":
                    addAlumni(request, response);
                    break;
                case "update":
                    updateAlumni(request, response);
                    break;
                default:
                    listAlumni(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listAlumni(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get parameters for search, sort, and pagination
        String searchTerm = request.getParameter("search");
        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder");
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            // Use default page 1
        }
        
        // Get alumni list with pagination
        List<Alumni> alumniList = alumniDAO.getAllAlumni(searchTerm, sortBy, sortOrder, page, PAGE_SIZE);
        int totalRecords = alumniDAO.getTotalAlumniCount(searchTerm);
        int totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);
        
        // Set attributes for JSP
        request.setAttribute("alumniList", alumniList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchTerm", searchTerm);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortOrder", sortOrder);
        
        request.getRequestDispatcher("/WEB-INF/views/alumni-list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Alumni alumni = alumniDAO.getAlumniById(id);
        request.setAttribute("alumni", alumni);
        request.getRequestDispatcher("/WEB-INF/views/edit-alumni.jsp").forward(request, response);
    }

    private void addAlumni(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Alumni alumni = new Alumni();
        alumni.setName(request.getParameter("name"));
        alumni.setEmail(request.getParameter("email"));
        alumni.setGraduationYear(request.getParameter("graduationYear"));
        alumni.setCourse(request.getParameter("course"));
        alumni.setCurrentPosition(request.getParameter("currentPosition"));
        alumni.setCompany(request.getParameter("company"));
        alumni.setPhoneNumber(request.getParameter("phoneNumber"));
        alumni.setAddress(request.getParameter("address"));
        
        if (alumniDAO.addAlumni(alumni)) {
            request.getSession().setAttribute("message", "Alumni added successfully!");
        } else {
            request.getSession().setAttribute("error", "Error adding alumni!");
        }
        response.sendRedirect(request.getContextPath() + "/alumni");
    }

    private void updateAlumni(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Alumni alumni = new Alumni();
        alumni.setId(Integer.parseInt(request.getParameter("id")));
        alumni.setName(request.getParameter("name"));
        alumni.setEmail(request.getParameter("email"));
        alumni.setGraduationYear(request.getParameter("graduationYear"));
        alumni.setCourse(request.getParameter("course"));
        alumni.setCurrentPosition(request.getParameter("currentPosition"));
        alumni.setCompany(request.getParameter("company"));
        alumni.setPhoneNumber(request.getParameter("phoneNumber"));
        alumni.setAddress(request.getParameter("address"));
        
        if (alumniDAO.updateAlumni(alumni)) {
            request.getSession().setAttribute("message", "Alumni updated successfully!");
        } else {
            request.getSession().setAttribute("error", "Error updating alumni!");
        }
        response.sendRedirect(request.getContextPath() + "/alumni");
    }

    private void deleteAlumni(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        if (alumniDAO.deleteAlumni(id)) {
            request.getSession().setAttribute("message", "Alumni deleted successfully!");
        } else {
            request.getSession().setAttribute("error", "Error deleting alumni!");
        }
        response.sendRedirect(request.getContextPath() + "/alumni");
    }
} // improve response handling
// handle alumni update request
// add request validation
