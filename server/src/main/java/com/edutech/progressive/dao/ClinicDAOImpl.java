package com.edutech.progressive.dao;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Clinic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClinicDAOImpl implements ClinicDAO {

    @Override
    public int addClinic(Clinic clinic) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int generatedID = -1;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "INSERT INTO clinic (clinic_name, location, doctor_id, contact_number, established_year) VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setString(1, clinic.getClinicName());
            statement.setString(2, clinic.getLocation());
            statement.setInt(3, clinic.getDoctorId());
            statement.setString(4, clinic.getContactNumber());
            statement.setInt(5, clinic.getEstablishedYear());

            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedID = resultSet.getInt(1);
                clinic.setClinicId(generatedID); // Set the generated ID back to the Clinic object
            }
        } catch (SQLException e) {
            System.err.println("Error adding clinic: " + e.getMessage());
            throw e; // Rethrow exception for the calling layer
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return generatedID;
    }

    @Override
    public Clinic getClinicById(int clinicId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM clinic WHERE clinic_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, clinicId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String clinicName = resultSet.getString("clinic_name");
                String location = resultSet.getString("location");
                int doctorId = resultSet.getInt("doctor_id");
                String contactNumber = resultSet.getString("contact_number");
                int establishedYear = resultSet.getInt("established_year");

                return new Clinic(clinicId, clinicName, location, doctorId, contactNumber, establishedYear);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching clinic by ID: " + e.getMessage());
            throw e; // Rethrow exception for the calling layer
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return null; // Return null if no record is found
    }

    @Override
    public void updateClinic(Clinic clinic) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "UPDATE clinic SET clinic_name = ?, location = ?, doctor_id = ?, contact_number = ?, established_year = ? WHERE clinic_id = ?";
            statement = connection.prepareStatement(sql);

            statement.setString(1, clinic.getClinicName());
            statement.setString(2, clinic.getLocation());
            statement.setInt(3, clinic.getDoctorId());
            statement.setString(4, clinic.getContactNumber());
            statement.setInt(5, clinic.getEstablishedYear());
            statement.setInt(6, clinic.getClinicId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating clinic: " + e.getMessage());
            throw e; // Rethrow exception for the calling layer
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public void deleteClinic(int clinicId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM clinic WHERE clinic_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, clinicId);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting clinic: " + e.getMessage());
            throw e; // Rethrow exception for the calling layer
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public List<Clinic> getAllClinics() throws SQLException {
        List<Clinic> clinicList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM clinic";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int clinicId = resultSet.getInt("clinic_id");
                String clinicName = resultSet.getString("clinic_name");
                String location = resultSet.getString("location");
                int doctorId = resultSet.getInt("doctor_id");
                String contactNumber = resultSet.getString("contact_number");
                int establishedYear = resultSet.getInt("established_year");

                clinicList.add(new Clinic(clinicId, clinicName, location, doctorId, contactNumber, establishedYear));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all clinics: " + e.getMessage());
            throw e; // Rethrow exception for the calling layer
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return clinicList;
    }
}

