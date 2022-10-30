/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojos;

import java.util.List;

/**
 *
 * @author mariadefarges
 */
public class Doctor {
    private final Integer doctorId;
    private String name;
    private String surname;
    private final String email;
    //private final String username;
    //private String password;

    public Doctor(Integer doctorId, String name, String surname, String email) {
        this.doctorId = doctorId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        //this.username = username;
        //this.password = password;
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public String getEmail() {
        return email;
    }

    /*public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/

    public void setPatients(List<Patient> patientsOfDoctor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
    
}
