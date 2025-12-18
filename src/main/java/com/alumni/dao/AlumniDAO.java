package com.alumni.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alumni.model.Alumni;
import com.alumni.util.DatabaseUtil;

public class AlumniDAO {
    private static final Logger LOGGER = Logger.getLogger(AlumniDAO.class.getName());
    
    public boolean addAlumni(Alumni alumni) {
        String sql = "INSERT INTO alumni (name, email, graduation_year, course, current_position, company, phone_number, address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, alumni.getName());
            pstmt.setString(2, alumni.getEmail());
            pstmt.setString(3, alumni.getGraduationYear());
            pstmt.setString(4, alumni.getCourse());
            pstmt.setString(5, alumni.getCurrentPosition());
            pstmt.setString(6, alumni.getCompany());
            pstmt.setString(7, alumni.getPhoneNumber());
            pstmt.setString(8, alumni.getAddress());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding new alumni", e);
            return false;
        }
    }
    
    public Alumni getAlumniById(int id) {
        String sql = "SELECT * FROM alumni WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAlumni(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving alumni with ID: " + id, e);
        }
        return null;
    }
    
    public List<Alumni> getAllAlumni(String searchTerm, String sortBy, String sortOrder, int page, int pageSize) {
        List<Alumni> alumniList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM alumni");
        
        // Add search condition if search term is provided
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sql.append(" WHERE name LIKE ? OR email LIKE ? OR course LIKE ? OR company LIKE ?");
        }
        
        // Add sorting
        if (sortBy != null && !sortBy.trim().isEmpty()) {
            // Map frontend column names to database column names
            String dbColumn = sortBy;
            if (sortBy.equals("graduationYear")) {
                dbColumn = "graduation_year";
            } else if (sortBy.equals("currentPosition")) {
                dbColumn = "current_position";
            } else if (sortBy.equals("phoneNumber")) {
                dbColumn = "phone_number";
            }
            sql.append(" ORDER BY ").append(dbColumn).append(" ").append(sortOrder != null ? sortOrder : "ASC");
        } else {
            sql.append(" ORDER BY id ASC"); // Default sort by ID
        }
        
        // Add pagination
        sql.append(" LIMIT ? OFFSET ?");
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                String searchPattern = "%" + searchTerm + "%";
                pstmt.setString(paramIndex++, searchPattern);
                pstmt.setString(paramIndex++, searchPattern);
                pstmt.setString(paramIndex++, searchPattern);
                pstmt.setString(paramIndex++, searchPattern);
            }
            
            pstmt.setInt(paramIndex++, pageSize);
            pstmt.setInt(paramIndex, (page - 1) * pageSize);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    alumniList.add(mapResultSetToAlumni(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving alumni list", e);
        }
        return alumniList;
    }
    
    public int getTotalAlumniCount(String searchTerm) {
        String sql = "SELECT COUNT(*) FROM alumni";
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sql += " WHERE name LIKE ? OR email LIKE ? OR course LIKE ? OR company LIKE ?";
        }
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                String searchPattern = "%" + searchTerm + "%";
                pstmt.setString(1, searchPattern);
                pstmt.setString(2, searchPattern);
                pstmt.setString(3, searchPattern);
                pstmt.setString(4, searchPattern);
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting total alumni count", e);
        }
        return 0;
    }
    
    public boolean updateAlumni(Alumni alumni) {
        String sql = "UPDATE alumni SET name=?, email=?, graduation_year=?, course=?, " +
                    "current_position=?, company=?, phone_number=?, address=? WHERE id=?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, alumni.getName());
            pstmt.setString(2, alumni.getEmail());
            pstmt.setString(3, alumni.getGraduationYear());
            pstmt.setString(4, alumni.getCourse());
            pstmt.setString(5, alumni.getCurrentPosition());
            pstmt.setString(6, alumni.getCompany());
            pstmt.setString(7, alumni.getPhoneNumber());
            pstmt.setString(8, alumni.getAddress());
            pstmt.setInt(9, alumni.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating alumni with ID: " + alumni.getId(), e);
            return false;
        }
    }
    
    public boolean deleteAlumni(int id) {
        String sql = "DELETE FROM alumni WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting alumni with ID: " + id, e);
            return false;
        }
    }
    
    private Alumni mapResultSetToAlumni(ResultSet rs) throws SQLException {
        Alumni alumni = new Alumni();
        alumni.setId(rs.getInt("id"));
        alumni.setName(rs.getString("name"));
        alumni.setEmail(rs.getString("email"));
        alumni.setGraduationYear(rs.getString("graduation_year"));
        alumni.setCourse(rs.getString("course"));
        alumni.setCurrentPosition(rs.getString("current_position"));
        alumni.setCompany(rs.getString("company"));
        alumni.setPhoneNumber(rs.getString("phone_number"));
        alumni.setAddress(rs.getString("address"));
        return alumni;
    }
} // add update alumni details
// add basic exception handling
// add delete alumni record
