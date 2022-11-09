/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;

import ifaces.PatientManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pojos.Patient;

/**
 *
 * @author mariadefarges
 */
public class JDBCPatientManager implements PatientManager {

    private JDBCManager manager;

    public JDBCPatientManager(JDBCManager m) {
        this.manager = m;
    }

    @Override
    public void addPatient(Patient p) throws SQLException {
        String sql = "INSERT INTO patients (name, surname, gender, birthDate, bloodType, email, diagnosis) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement prep = manager.getConnection().prepareStatement(sql);
        prep.setString(1, p.getName());
        prep.setString(2, p.getSurname());
        prep.setString(3, p.getGender());
        prep.setDate(4, p.getBirthDate());
        prep.setString(5, p.getBloodType());
        prep.setString(6, p.getEmail());
        prep.setString(7, p.getDiagnosis());
        prep.executeUpdate();
        prep.close();
    }

    @Override
    public Patient searchPatientById(int patientId) throws SQLException {
        Patient p = null;
        String sql = "SELECT * FROM patients WHERE patientId= ?";
        PreparedStatement prep = manager.getConnection().prepareStatement(sql);
        prep.setInt(1, patientId);
        ResultSet rs = prep.executeQuery();
        while (rs.next()) {
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String gender = rs.getString("gender");
            Date birthDate = rs.getDate("birthDate");
            String bloodType = rs.getString("bloodType");
            String email = rs.getString("email");
            String diagnosis = rs.getString("diagnosis");
            p = new Patient(patientId, name, surname, gender, birthDate, bloodType, email, diagnosis);
        }
        prep.close();
        rs.close();
        return p;
    }

    @Override
    public List<Patient> searchPatientbyName(String name) throws SQLException {
        Patient p = null;
        String sql = "SELECT * FROM patients WHERE name = ?";
        PreparedStatement prep = manager.getConnection().prepareStatement(sql);
        prep.setString(1, name);
        ResultSet rs = prep.executeQuery();
        List<Patient> patients = new ArrayList<Patient>();
        while (rs.next()) {
            int id = rs.getInt("patientId");
            String surname = rs.getString("surname");
            String gender = rs.getString("gender");
            Date birthDate = rs.getDate("birthDate");
            String bloodType = rs.getString("bloodType");
            String email = rs.getString("email");
            String diagnosis = rs.getString("diagnosis");
            p = new Patient(id, name, surname, gender, birthDate, bloodType, email, diagnosis);
            patients.add(p);
        }
        rs.close();
        return patients;
    }

    @Override
    public List<Patient> searchPatientbySurname(String surname) throws SQLException {
        Patient p = null;
        String sql = "SELECT * FROM patients WHERE surname = ?";
        PreparedStatement prep = manager.getConnection().prepareStatement(sql);
        prep.setString(1, surname);
        ResultSet rs = prep.executeQuery();
        List<Patient> patients = new ArrayList<Patient>();
        while (rs.next()) {
            int id = rs.getInt("patientId");
            String name = rs.getString("name");
            String gender = rs.getString("gender");
            Date birthDate = rs.getDate("birthDate");
            String bloodType = rs.getString("bloodType");
            String email = rs.getString("email");
            String diagnosis = rs.getString("diagnosis");
            p = new Patient(id, name, surname, gender, birthDate, bloodType, email, diagnosis);
            patients.add(p);
        }
        rs.close();
        return patients;
    }

    @Override
    public String getPatientsFullNameById(int patientId) throws SQLException {
        String sql = "SELECT name, surname FROM patients WHERE patientId = ?";
        PreparedStatement prep = manager.getConnection().prepareStatement(sql);
        ResultSet rs = prep.executeQuery();
        prep.setInt(1, patientId);
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String fullName = name + " " + surname;
        prep.close();
        rs.close();
        return fullName;
    }

    @Override
    public Patient getPatientByUserId(int userId) throws SQLException {
        Patient p = null;
        String sql = "SELECT * FROM patients WHERE userId = ?";
        PreparedStatement prep = manager.getConnection().prepareStatement(sql);
        prep.setInt(1, userId);
        ResultSet rs = prep.executeQuery();
        if (rs.next()) {
            p = new Patient(rs.getInt("patientId"), rs.getString("name"), 
                    rs.getString("surname"), rs.getString("gender"),
                    rs.getDate("birthDate"), rs.getString("bloodType"), 
                    rs.getString("email"), rs.getString("diagnosis"), userId);
        }
        prep.close();
        rs.close();
        return p;

    }

}
