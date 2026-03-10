package com.edutech.progressive.dao;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImpl implements PatientDAO {

    @Override
    public int addPatient(Patient patient) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int generatedID = -1;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "INSERT INTO patient (full_name, date_of_birth, contact_number, email, address) VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setString(1, patient.getFullName());
            statement.setDate(2, new java.sql.Date(patient.getDateOfBirth().getTime())); // Convert java.util.Date to java.sql.Date
            statement.setString(3, patient.getContactNumber());
            statement.setString(4, patient.getEmail());
            statement.setString(5, patient.getAddress());

            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedID = resultSet.getInt(1);
                patient.setPatientId(generatedID); // Set the generated ID back to the Patient object
            }
        } catch (SQLException e) {
            System.err.println("Error adding patient: " + e.getMessage());
            throw e; // Rethrow exception for the calling layer
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return generatedID;
    }

    @Override
    public Patient getPatientById(int patientId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM patient WHERE patient_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, patientId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String fullName = resultSet.getString("full_name");
                java.util.Date dateOfBirth = resultSet.getDate("date_of_birth");
                String contactNumber = resultSet.getString("contact_number");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");

                return new Patient(patientId, fullName, dateOfBirth, contactNumber, email, address);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching patient by ID: " + e.getMessage());
            throw e;
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return null; // Return null if no record is found
    }

    @Override
    public void updatePatient(Patient patient) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "UPDATE patient SET full_name = ?, date_of_birth = ?, contact_number = ?, email = ?, address = ? WHERE patient_id = ?";
            statement = connection.prepareStatement(sql);

            statement.setString(1, patient.getFullName());
            statement.setDate(2, new java.sql.Date(patient.getDateOfBirth().getTime())); // Convert java.util.Date to java.sql.Date
            statement.setString(3, patient.getContactNumber());
            statement.setString(4, patient.getEmail());
            statement.setString(5, patient.getAddress());
            statement.setInt(6, patient.getPatientId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating patient: " + e.getMessage());
            throw e; // Rethrow exception for the calling layer
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public void deletePatient(int patientId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM patient WHERE patient_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, patientId);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting patient: " + e.getMessage());
            throw e; // Rethrow exception for the calling layer
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM patient";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int patientId = resultSet.getInt("patient_id");
                String fullName = resultSet.getString("full_name");
                java.util.Date dateOfBirth = resultSet.getDate("date_of_birth");
                String contactNumber = resultSet.getString("contact_number");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");

                patientList.add(new Patient(patientId, fullName, dateOfBirth, contactNumber, email, address));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all patients: " + e.getMessage());
            throw e; // Rethrow exception for the calling layer
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return patientList;
    }
}
