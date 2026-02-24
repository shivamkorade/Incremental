package com.edutech.progressive.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Doctor;

public class DoctorDAOImpl implements DoctorDAO {

    Connection connection;

    public DoctorDAOImpl(){
        try {
            this.connection = DatabaseConnectionManager.getConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public int addDoctor(Doctor doctor) throws SQLException {
    String sql = "INSERT INTO doctor (full_name,specialty,contact_number,email,year_of_experience) values (?,?,?,?,?)";
        PreparedStatement smt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        smt.setString(1,doctor.getFullName());
       smt.setString(2,doctor.getSpecialty());
        smt.setString(3,doctor.getContactNumber());
        smt.setString(4,doctor.getEmail());
        smt.setInt(5,doctor.getYearsOfExperience());
        smt.executeUpdate();

        ResultSet rs = smt.getGeneratedKeys();
        if(rs.next()){
            doctor.setDoctorId(rs.getInt(1));
            return 1;
        }
        return -1;
    }

    @Override
    public Doctor getDoctorById(int doctorId) throws SQLException {
         String sql = "Select * from doctor where doctor_id=?";
        PreparedStatement smt = connection.prepareStatement(sql);
        smt.setInt(1, doctorId);
        ResultSet rs = smt.executeQuery();
        if(rs.next()){
            int id = rs.getInt("doctor_id");
            String name = rs.getString("full_name");
            String specialty = rs.getString("specialty");
            String number = rs.getString("contact_number");
            String email = rs.getString("email");
            int exp = rs.getInt("year_of_experience");

            Doctor doctor = new Doctor(id, name, specialty, number, email, exp);
            return doctor;
        }
        return null;
    }

    @Override
    public void updateDoctor(Doctor doctor) throws SQLException {
         String sql = "update doctor set full_name=?,specialty=?,contact_number=?,email=?,year_of_experience=? where doctor_id=?";
        PreparedStatement smt = connection.prepareStatement(sql);
        smt.setString(1,doctor.getFullName());
        smt.setString(2,doctor.getSpecialty());
        smt.setString(3,doctor.getContactNumber());
        smt.setString(4,doctor.getEmail());
        smt.setInt(5,doctor.getYearsOfExperience());
        smt.setInt(6,doctor.getDoctorId());
        smt.executeUpdate();
    }

    @Override
    public void deleteDoctor(int doctorId) throws SQLException {
        String sql = "delete from doctor where doctor_id=?";
        PreparedStatement smt = connection.prepareStatement(sql);
        smt.setInt(1, doctorId);
        smt.executeUpdate();
    }

    @Override
    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctorList = new  ArrayList<>();
        String sql = "Select * from doctor";
        PreparedStatement smt = connection.prepareStatement(sql);
        
        ResultSet rs = smt.executeQuery();
        while(rs.next()){
            int id = rs.getInt("doctor_id");
            String name = rs.getString("full_name");
            String specialty = rs.getString("specialty");
            String number = rs.getString("contact_number");
            String email = rs.getString("email");
            int exp = rs.getInt("year_of_experience");

            Doctor doctor = new Doctor(id, name, specialty, number, email, exp);
            doctorList.add(doctor);
        }
        return doctorList;
    }
    



}
