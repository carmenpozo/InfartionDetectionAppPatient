/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ifaces;

import pojos.Patient;
import java.sql.*;
import java.util.List;

/**
 *
 * @author mariadefarges
 */
public interface PatientManager {
   
    public void addPatient(Patient p) throws SQLException;
    
    public List<Patient> searchPatientbyName (String name) throws SQLException;
	
    public List<Patient> searchPatientbySurname (String surname) throws SQLException;
	
    public Patient searchPatientById(int patientId) throws SQLException, Exception;
    
    public String getPatientsFullNameById(int patientId) throws SQLException; 
    
    public Patient getPatientByUserId(int userId) throws SQLException;

}
