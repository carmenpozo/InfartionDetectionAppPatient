/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojos;

import java.io.Serializable;
import java.util.Objects;
import java.sql.Date;


/**
 *
 * @author mariadefarges
 */
public class Patient implements Serializable {
    
    private static final long serialVersionUID = -1L;
    
    private Integer patientId;
    private String name;
    private String surname;
    private String gender;
    private Date birthDate;
    private String bloodType;
    private String email;
    private byte[] password;
    private String diagnosis;
    private String bitalino;
    private Integer userId;

    public Patient(Integer patientId, String name, String surname, String gender, Date birthDate, String bloodType, String email, byte[] password ,String diagnosis, String bitalino,Integer userId) {
        this.patientId = patientId;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.bloodType = bloodType;
        this.email = email;
        this.password = password;
        this.diagnosis = diagnosis;
        this.bitalino = bitalino;
        this.userId= userId;
     
    }

    public Patient(Integer patientId, String name, String surname, String gender, Date birthDate, String bloodType, String email, byte[] password, String diagnosis) {
        this.patientId = patientId;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.bloodType = bloodType;
        this.email = email;
        this.password = password;
        this.diagnosis = diagnosis;
        this.bitalino = null;
    }

    public Patient(String email, byte[] hash) {
        this.email = email;
        this.password = hash;
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
    public byte[] getPassword() {
        return password;
    }
    
    public void setPassword(byte[] password) {
        this.password = password;
    }
    

    public String getDiagnosis() {
        return diagnosis;
    }
    
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    
    public String getBitalino() {
        return bitalino;
    }

    public void setBitalino(String bitalino) {
        this.bitalino = bitalino;
    }
    
    public Integer getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Patient{" + "patientId=" + patientId + ", name=" + name + ", surname=" + surname +
                ", gender=" + gender + ", birthDate=" + birthDate + ", bloodType=" + bloodType + 
                ", email=" + email + ", diagnosis=" + diagnosis + ", bitalino=" + bitalino + ", userId=" + userId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.patientId);
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.surname);
        hash = 73 * hash + Objects.hashCode(this.gender);
        hash = 73 * hash + Objects.hashCode(this.birthDate);
        hash = 73 * hash + Objects.hashCode(this.bloodType);
        hash = 73 * hash + Objects.hashCode(this.email);
        hash = 73 * hash + Objects.hashCode(this.password);
        hash = 73 * hash + Objects.hashCode(this.diagnosis);
        hash = 73 * hash + Objects.hashCode(this.bitalino);
        hash = 73 * hash + Objects.hashCode(this.userId);
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
        if (!Objects.equals(this.bitalino, other.bitalino)) {
            return false;
        }
        return Objects.equals(this.userId, other.userId);
    }

    

}
