package com.alumni.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import com.alumni.util.DatabaseUtil;

@WebServlet("/db-test")
public class DatabaseTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Database Connection Test</h1>");
        
        try {
            Connection conn = DatabaseUtil.getConnection();
            DatabaseMetaData metaData = conn.getMetaData();
            
            out.println("<h2>Connection Information:</h2>");
            out.println("<p>Database URL: " + metaData.getURL() + "</p>");
            out.println("<p>Database User: " + metaData.getUserName() + "</p>");
            out.println("<p>Database Product: " + metaData.getDatabaseProductName() + "</p>");
            out.println("<p>Database Version: " + metaData.getDatabaseProductVersion() + "</p>");
            
            out.println("<h2>Table Information:</h2>");
            ResultSet tables = metaData.getTables(null, null, "alumni", new String[] {"TABLE"});
            if (tables.next()) {
                out.println("<p style='color: green;'>Alumni table exists!</p>");
                
                // Get column information
                out.println("<h3>Table Columns:</h3>");
                out.println("<table border='1'>");
                out.println("<tr><th>Column Name</th><th>Type</th><th>Size</th></tr>");
                ResultSet columns = metaData.getColumns(null, null, "alumni", null);
                while (columns.next()) {
                    out.println("<tr>");
                    out.println("<td>" + columns.getString("COLUMN_NAME") + "</td>");
                    out.println("<td>" + columns.getString("TYPE_NAME") + "</td>");
                    out.println("<td>" + columns.getString("COLUMN_SIZE") + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                
                // Get row count
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM alumni");
                if (rs.next()) {
                    out.println("<p>Number of records: " + rs.getInt(1) + "</p>");
                }
                
                // Show sample data
                out.println("<h3>Sample Data:</h3>");
                rs = stmt.executeQuery("SELECT * FROM alumni LIMIT 5");
                if (rs.next()) {
                    out.println("<table border='1'>");
                    out.println("<tr>");
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        out.println("<th>" + rs.getMetaData().getColumnName(i) + "</th>");
                    }
                    out.println("</tr>");
                    do {
                        out.println("<tr>");
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            out.println("<td>" + rs.getString(i) + "</td>");
                        }
                        out.println("</tr>");
                    } while (rs.next());
                    out.println("</table>");
                } else {
                    out.println("<p style='color: orange;'>No records found in the alumni table.</p>");
                }
            } else {
                out.println("<p style='color: red;'>Alumni table does not exist!</p>");
            }
            
            conn.close();
            out.println("<p style='color: green;'>Database connection successful!</p>");
        } catch (Exception e) {
            out.println("<p style='color: red;'>Error connecting to database!</p>");
            out.println("<p>Error: " + e.getMessage() + "</p>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }
        
        out.println("</body></html>");
    }
} 