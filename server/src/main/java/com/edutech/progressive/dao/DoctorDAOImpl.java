package com.edutech.progressive.dao;
import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAOImpl implements DoctorDAO {

    @Override
    public int addDoctor(Doctor doctor) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int generatedID = -1;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "INSERT INTO doctor (full_name, specialty, contact_number, email, years_of_experience) VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setString(1, doctor.getFullName());
            statement.setString(2, doctor.getSpecialty());
            statement.setString(3, doctor.getContactNumber());
            statement.setString(4, doctor.getEmail());
            statement.setInt(5, doctor.getYearsOfExperience());

            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedID = resultSet.getInt(1);
                doctor.setDoctorId(generatedID);
            }
        } catch (SQLException e) {
            System.err.println("Error adding doctor: " + e.getMessage());
            throw e; // Rethrow exception to the calling layer
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return generatedID;
    }

    @Override
    public Doctor getDoctorById(int doctorId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM doctor WHERE doctor_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, doctorId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String fullName = resultSet.getString("full_name");
                String specialty = resultSet.getString("specialty");
                String contactNumber = resultSet.getString("contact_number");
                String email = resultSet.getString("email");
                int yearsOfExperience = resultSet.getInt("years_of_experience");

                return new Doctor(doctorId, fullName, specialty, contactNumber, email, yearsOfExperience);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching doctor by ID: " + e.getMessage());
            throw e; // Rethrow exception to the calling layer
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return null; // Return null if no record is found
    }

    @Override
    public void updateDoctor(Doctor doctor) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "UPDATE doctor SET full_name = ?, specialty = ?, contact_number = ?, email = ?, years_of_experience = ? WHERE doctor_id = ?";
            statement = connection.prepareStatement(sql);

            statement.setString(1, doctor.getFullName());
            statement.setString(2, doctor.getSpecialty());
            statement.setString(3, doctor.getContactNumber());
            statement.setString(4, doctor.getEmail());
            statement.setInt(5, doctor.getYearsOfExperience());
            statement.setInt(6, doctor.getDoctorId());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating doctor: " + e.getMessage());
            throw e; // Rethrow exception to the calling layer
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public void deleteDoctor(int doctorId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "DELETE FROM doctor WHERE doctor_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, doctorId);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting doctor: " + e.getMessage());
            throw e; // Rethrow exception to the calling layer
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctorList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnectionManager.getConnection();
            String sql = "SELECT * FROM doctor";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int doctorId = resultSet.getInt("doctor_id");
                String fullName = resultSet.getString("full_name");
                String specialty = resultSet.getString("specialty");
                String contactNumber = resultSet.getString("contact_number");
                String email = resultSet.getString("email");
                int yearsOfExperience = resultSet.getInt("years_of_experience");

                doctorList.add(new Doctor(doctorId, fullName, specialty, contactNumber, email, yearsOfExperience));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all doctors: " + e.getMessage());
            throw e; // Rethrow exception to the calling layer
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return doctorList;
    }
}
