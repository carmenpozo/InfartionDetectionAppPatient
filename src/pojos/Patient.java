/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojos;

import BITalino.BITalino;
import java.io.Serializable;
import java.util.Objects;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author mariadefarges
 */
public class Patient implements Serializable {
    
    private static final long serialVersionUID = -1L;
    
    private final Integer patientId;
    private String name;
    private String surname;
    private String gender;
    private final Date birthDate;
    private final String bloodType;
    private final String email;
    private String diagnosis;
    private BITalino bitalino;
    private List<Doctor> doctors;
    //private  String username;
    //private String password;

    public Patient(Integer patientId, String name, String surname, String gender, Date birthDate, String bloodType, String email, String diagnosis, BITalino bitalino) {
        this.patientId = patientId;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.bloodType = bloodType;
        this.email = email;
        this.diagnosis = diagnosis;
        this.bitalino = bitalino;
    }

    public Patient(Integer patientId, String name, String surname, String gender, Date birthDate, String bloodType, String email, String diagnosis) {
        this.patientId = patientId;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.bloodType = bloodType;
        this.email = email;
        this.diagnosis = diagnosis;
    }
    


    public Integer getPatientId() {
        return patientId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getEmail() {
        return email;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public BITalino getBitalino() {
        return bitalino;
    }

    public void setBitalino(BITalino bitalino) {
        this.bitalino = bitalino;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.patientId);
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.surname);
        hash = 89 * hash + Objects.hashCode(this.gender);
        hash = 89 * hash + Objects.hashCode(this.birthDate);
        hash = 89 * hash + Objects.hashCode(this.bloodType);
        hash = 89 * hash + Objects.hashCode(this.email);
        hash = 89 * hash + Objects.hashCode(this.diagnosis);
        hash = 89 * hash + Objects.hashCode(this.bitalino);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Patient other = (Patient) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.gender, other.gender)) {
            return false;
        }
        if (!Objects.equals(this.bloodType, other.bloodType)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.diagnosis, other.diagnosis)) {
            return false;
        }
        if (!Objects.equals(this.patientId, other.patientId)) {
            return false;
        }
        if (!Objects.equals(this.birthDate, other.birthDate)) {
            return false;
        }
        return Objects.equals(this.bitalino, other.bitalino);
    }
    
    
    
    

    
}
