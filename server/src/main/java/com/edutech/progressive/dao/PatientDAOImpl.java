package com.edutech.progressive.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Patient;

public class PatientDAOImpl implements PatientDAO {
    Connection connection;

    public PatientDAOImpl(){
        try {
            this.connection = DatabaseConnectionManager.getConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public int addPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO PATIENT (full_name,date_of_birth,contact_number,email,address) values (?,?,?,?,?)";
        PreparedStatement smt = connection.prepareStatement(sql);
        smt.setString(1,patient.getFullName());
        smt.setDate(2,(Date) (patient.getDateOfBirth()));
        smt.setString(3,patient.getContactNumber());
        smt.setString(4,patient.getEmail());
        smt.setString(5,patient.getAddress());
        int i = smt.executeUpdate();
        return i;
        
        }
       
    

    @Override
    public Patient getPatientById(int patientId) throws SQLException {
        String sql = "Select * from patient where patient_id=?";
        PreparedStatement smt = connection.prepareStatement(sql);
        smt.setInt(1, patientId);
        ResultSet rs = smt.executeQuery();
        if(rs.next()){
            int id = rs.getInt("patient_id");
            String name = rs.getString("full_name");
            Date date = rs.getDate("date_of_birth");
            String number = rs.getString("contact_number");
            String email = rs.getString("email");
            String address = rs.getString("address");

            Patient patient = new Patient(id, name, date, number, email, address);
            return patient;
        }
        return null;
    }

    @Override
    public void updatePatient(Patient patient) throws SQLException {
        String sql = "update patient set full_name=?,date_of_birth=?,contact_number=?,email=?,address=? where patient_id=?";
        PreparedStatement smt = connection.prepareStatement(sql);
        smt.setString(1,patient.getFullName());
        smt.setDate(2,(Date) (patient.getDateOfBirth()));
        smt.setString(3,patient.getContactNumber());
        smt.setString(4,patient.getEmail());
        smt.setString(5,patient.getAddress());
        smt.setInt(6,patient.getPatientId());
        smt.executeUpdate();
    }

    @Override
    public void deletePatient(int patientId) throws SQLException {
        String sql = "delete from patient where patient_id=?";
        PreparedStatement smt = connection.prepareStatement(sql);
        smt.setInt(1, patientId);
        smt.executeUpdate();
        
        
    }

    @Override
    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patientList = new ArrayList<>();
        String sql = "Select * from patient";
        PreparedStatement smt = connection.prepareStatement(sql);
        ResultSet rs = smt.executeQuery();
        while(rs.next()){
            int id = rs.getInt("patient_id");
            String name = rs.getString("full_name");
            Date date = rs.getDate("date_of_birth");
            String number = rs.getString("contact_number");
            String email = rs.getString("email");
            String address = rs.getString("address");

            Patient patient = new Patient(id, name, date, number, email, address);
            patientList.add(patient);
            
        }
        return patientList;
        
    }
    

}
