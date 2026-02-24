package com.edutech.progressive.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Clinic;

public class ClinicDAOImpl implements ClinicDAO{ 
    
     Connection connection;

    public ClinicDAOImpl(){
        try {
            this.connection = DatabaseConnectionManager.getConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public int addClinic(Clinic clinic) throws SQLException {
        String sql = "INSERT INTO clinic (clinic_name,location,doctor_id,contact_number,established_year) values (?,?,?,?,?)";
        PreparedStatement smt = connection.prepareStatement(sql);
        smt.setString(1,clinic.getClinicName());
       smt.setString(2,clinic.getLocation());
        smt.setInt(3,clinic.getDoctorId());
        smt.setString(4,clinic.getContactNumber());
        smt.setInt(5,clinic.getEstablishedYear());
       
        int i= smt.executeUpdate();
        return i;
       
    }

    @Override
    public Clinic getClinicById(int clinicId) throws SQLException {
        String sql = "select * from clinic where clinic_id=?";
        PreparedStatement smt = connection.prepareStatement(sql);
        smt.setInt(1, clinicId);
        ResultSet rs = smt.executeQuery();
        if(rs.next()){
            int id = rs.getInt("clinic_id");
            String name = rs.getString("clinic_name");
            String location = rs.getString("location");
            int dId = rs.getInt("doctor_id");
            String number = rs.getString("contact_number");
            int year =rs.getInt("established_year");

            Clinic clinic = new Clinic(id, name, location, dId, number, year);
            return clinic;
        }
        return null;
    }

    @Override
    public void updateClinic(Clinic clinic) throws SQLException {
        String sql = "update clinic set clinic_name=?,location=?,doctor_id=?,contact_number=?,established_year=? where clinic_id=?";
        PreparedStatement smt =connection.prepareStatement(sql);
        smt.setString(1,clinic.getClinicName());
       smt.setString(2,clinic.getLocation());
        smt.setInt(3,clinic.getDoctorId());
        smt.setString(4,clinic.getContactNumber());
        smt.setInt(5,clinic.getEstablishedYear());
        smt.setInt(6,clinic.getClinicId());
        smt.executeUpdate();
        
        
    }

    @Override
    public void deleteClinic(int clinicId) throws SQLException {
        String sql = "delete from clinic where clinic_id=?";
        PreparedStatement smt = connection.prepareStatement(sql);
        smt.setInt(1, clinicId);
        smt.executeUpdate();
        
    }

    @Override
    public List<Clinic> getAllClinics() throws SQLException {
        List<Clinic> clinicList = new ArrayList<>();
        String sql = "select * from clinic";
        PreparedStatement smt = connection.prepareStatement(sql);
        ResultSet rs = smt.executeQuery();
        while(rs.next()){
            int id = rs.getInt("clinic_id");
            String name = rs.getString("clinic_name");
            String location = rs.getString("location");
            int dId = rs.getInt("doctor_id");
            String number = rs.getString("contact_number");
            int year =rs.getInt("established_year");

            Clinic clinic = new Clinic(id, name, location, dId, number, year);
            clinicList.add(clinic);
        }
        return clinicList;
    }
    

}
