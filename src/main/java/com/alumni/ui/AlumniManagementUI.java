package com.alumni.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.alumni.dao.AlumniDAO;
import com.alumni.model.Alumni;
import com.formdev.flatlaf.FlatLightLaf;

public class AlumniManagementUI extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(AlumniManagementUI.class.getName());
    private final AlumniDAO alumniDAO;
    private JTable alumniTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, emailField, graduationYearField, courseField,
                      currentPositionField, companyField, phoneNumberField, addressField;

    public AlumniManagementUI() {
        alumniDAO = new AlumniDAO();
        initializeUI();
        loadAlumniData();
    }

    private void initializeUI() {
        setTitle("Alumni Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create form panel
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.NORTH);

        // Create table panel
        JPanel tablePanel = createTablePanel();
        mainPanel.add(new JScrollPane(tablePanel), BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Alumni Information"));

        // Create text fields
        nameField = new JTextField();
        emailField = new JTextField();
        graduationYearField = new JTextField();
        courseField = new JTextField();
        currentPositionField = new JTextField();
        companyField = new JTextField();
        phoneNumberField = new JTextField();
        addressField = new JTextField();

        // Add components to panel
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Graduation Year:"));
        panel.add(graduationYearField);
        panel.add(new JLabel("Course:"));
        panel.add(courseField);
        panel.add(new JLabel("Current Position:"));
        panel.add(currentPositionField);
        panel.add(new JLabel("Company:"));
        panel.add(companyField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneNumberField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);

        return panel;
    }

    private JPanel createTablePanel() {
        String[] columns = {"ID", "Name", "Email", "Graduation Year", "Course", "Position", "Company", "Phone"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        alumniTable = new JTable(tableModel);
        alumniTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        alumniTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedAlumni();
            }
        });

        return new JPanel(new BorderLayout()) {{
            add(new JScrollPane(alumniTable), BorderLayout.CENTER);
        }};
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");

        addButton.addActionListener(e -> addAlumni());
        updateButton.addActionListener(e -> updateAlumni());
        deleteButton.addActionListener(e -> deleteAlumni());
        clearButton.addActionListener(e -> clearForm());

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(clearButton);

        return panel;
    }

    private void loadAlumniData() {
        tableModel.setRowCount(0);
        try {
            List<Alumni> alumniList = alumniDAO.getAllAlumni();
            for (Alumni alumni : alumniList) {
                Object[] row = {
                    alumni.getId(),
                    alumni.getName(),
                    alumni.getEmail(),
                    alumni.getGraduationYear(),
                    alumni.getCourse(),
                    alumni.getCurrentPosition(),
                    alumni.getCompany(),
                    alumni.getPhoneNumber()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading alumni data", e);
            JOptionPane.showMessageDialog(this, 
                "Error loading alumni data: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSelectedAlumni() {
        int selectedRow = alumniTable.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            Alumni alumni = alumniDAO.getAlumniById(id);
            if (alumni != null) {
                nameField.setText(alumni.getName());
                emailField.setText(alumni.getEmail());
                graduationYearField.setText(alumni.getGraduationYear());
                courseField.setText(alumni.getCourse());
                currentPositionField.setText(alumni.getCurrentPosition());
                companyField.setText(alumni.getCompany());
                phoneNumberField.setText(alumni.getPhoneNumber());
                addressField.setText(alumni.getAddress());
            }
        }
    }

    private void addAlumni() {
        try {
            Alumni alumni = new Alumni();
            alumni.setName(nameField.getText());
            alumni.setEmail(emailField.getText());
            alumni.setGraduationYear(graduationYearField.getText());
            alumni.setCourse(courseField.getText());
            alumni.setCurrentPosition(currentPositionField.getText());
            alumni.setCompany(companyField.getText());
            alumni.setPhoneNumber(phoneNumberField.getText());
            alumni.setAddress(addressField.getText());

            if (alumniDAO.addAlumni(alumni)) {
                JOptionPane.showMessageDialog(this, "Alumni added successfully!");
                clearForm();
                loadAlumniData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add alumni", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding alumni", e);
            JOptionPane.showMessageDialog(this, 
                "Error adding alumni: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateAlumni() {
        try {
            int selectedRow = alumniTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                Alumni alumni = alumniDAO.getAlumniById(id);
                if (alumni != null) {
                    alumni.setName(nameField.getText());
                    alumni.setEmail(emailField.getText());
                    alumni.setGraduationYear(graduationYearField.getText());
                    alumni.setCourse(courseField.getText());
                    alumni.setCurrentPosition(currentPositionField.getText());
                    alumni.setCompany(companyField.getText());
                    alumni.setPhoneNumber(phoneNumberField.getText());
                    alumni.setAddress(addressField.getText());

                    if (alumniDAO.updateAlumni(alumni)) {
                        JOptionPane.showMessageDialog(this, "Alumni updated successfully!");
                        clearForm();
                        loadAlumniData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update alumni", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an alumni to update");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating alumni", e);
            JOptionPane.showMessageDialog(this, 
                "Error updating alumni: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAlumni() {
        try {
            int selectedRow = alumniTable.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this alumni?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    if (alumniDAO.deleteAlumni(id)) {
                        JOptionPane.showMessageDialog(this, "Alumni deleted successfully!");
                        clearForm();
                        loadAlumniData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete alumni", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an alumni to delete");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting alumni", e);
            JOptionPane.showMessageDialog(this, 
                "Error deleting alumni: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        graduationYearField.setText("");
        courseField.setText("");
        currentPositionField.setText("");
        companyField.setText("");
        phoneNumberField.setText("");
        addressField.setText("");
        alumniTable.clearSelection();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error setting look and feel", e);
        }
        
        SwingUtilities.invokeLater(() -> {
            new AlumniManagementUI().setVisible(true);
        });
    }
} 