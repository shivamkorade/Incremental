package com.edutech.progressive.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Patient;

public class PatientDAOImpl implements PatientDAO {
    Connection connection;
    

    public PatientDAOImpl(){
        try {
            this.connection = DatabaseConnectionManager.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    @Override
    public int addPatient(Patient patient) throws SQLException {
        String sql = "insert into patient (full_name,date_of_birth,contact_number,email,address) values (?,?,?,?,?)";
        java.util.Date dob = patient.getDateOfBirth();
        PreparedStatement smt;
            smt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            smt.setString(1,patient.getFullName());
        smt.setDate(2,new java.sql.Date(dob.getTime()));
        smt.setString(3,patient.getContactNumber());
        smt.setString(4,patient.getEmail());
        smt.setString(5,patient.getAddress());
        int a = smt.executeUpdate();

        ResultSet rs = smt.getGeneratedKeys();
        if(rs.next()){
            int i = rs.getInt(1);
            patient.setPatientId(i);
            return i;
        }
        
        return -1;
        
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
        java.util.Date dob = patient.getDateOfBirth();
        int i=0;
        for(Patient p:getAllPatients()){
             if(p.getPatientId()==patient.getPatientId())
             {
                i=1;
                 String sql = "update patient set full_name=?,date_of_birth=?,contact_number=?,email=?,address=? where patient_id=?";
        PreparedStatement smt = connection.prepareStatement(sql);
        smt.setString(1,patient.getFullName());
        smt.setDate(2,new java.sql.Date(dob.getTime()));
        smt.setString(3,patient.getContactNumber());
        smt.setString(4,patient.getEmail());
        smt.setString(5,patient.getAddress());
        smt.setInt(6,patient.getPatientId());
        smt.executeUpdate();
             }
        }
       
        if(i==0){
            throw new SQLException();
        }

    }

    @Override
    public void deletePatient(int patientId) throws SQLException {
        
        // System.out.println(getAllPatients());
        int i=0;
        for(Patient p:getAllPatients()){
            if(p.getPatientId()==patientId){
                i=1;
                String sql = "delete from patient where patient_id=?";
                PreparedStatement smt = connection.prepareStatement(sql);
                smt.setInt(1, patientId);
                smt.executeUpdate();
                
            }
        }
        
        if(i==0){
            throw new SQLException();
        }
        
        
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
