/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojos;

import java.io.Serializable;
import java.util.Objects;
import java.sql.Date;
import java.util.Arrays;


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
    private final Date birthDate;
    private final String bloodType;
    private final String email;
    private String password;
    private String symptoms;
    private String bitalino;

    public Patient(Integer patientId, String name, String surname, String gender, Date birthDate, String bloodType, String email, String password, String symptoms, String bitalino) {
        this.patientId = patientId;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.bloodType = bloodType;
        this.email = email;
        this.password = password;
        this.symptoms = symptoms;
        this.bitalino = bitalino;
     
    }
    
        public Patient(String name, String surname, String gender, Date birthDate, String bloodType, String email, String password ,String symptoms, String bitalino) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.bloodType = bloodType;
        this.email = email;
        this.password = password;
        this.symptoms = symptoms;
        this.bitalino = bitalino;
     
    }


    public Integer getPatientId() {
        return patientId;
    }
    public void setPatientId(Integer PatientId) {
        this.patientId = patientId;
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
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
    
    public String getBitalino() {
        return bitalino;
    }

    public void setBitalino(String bitalino) {
        this.bitalino = bitalino;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.patientId);
        hash = 31 * hash + Objects.hashCode(this.name);
        hash = 31 * hash + Objects.hashCode(this.surname);
        hash = 31 * hash + Objects.hashCode(this.gender);
        hash = 31 * hash + Objects.hashCode(this.birthDate);
        hash = 31 * hash + Objects.hashCode(this.bloodType);
        hash = 31 * hash + Objects.hashCode(this.email);
        hash = 31 * hash + Objects.hashCode(this.password);
        hash = 31 * hash + Objects.hashCode(this.symptoms);
        hash = 31 * hash + Objects.hashCode(this.bitalino);
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
        if (!Objects.equals(this.symptoms, other.symptoms)) {
            return false;
        }
        if (!Objects.equals(this.bitalino, other.bitalino)) {
            return false;
        }
        if (!Objects.equals(this.patientId, other.patientId)) {
            return false;
        }
        if (!Objects.equals(this.birthDate, other.birthDate)) {
            return false;
        }
        return Objects.equals(this.password, other.password);
    }

    @Override
    public String toString() {
        return "Patient{" + "patientId=" + patientId + ", name=" + name + ", surname=" + surname 
                + ", gender=" + gender + ", birthDate=" + birthDate + ", bloodType=" + bloodType 
                + ", email=" + email + ", password=" + password + ", symptoms=" + symptoms + ", bitalino=" + bitalino + '}';
    }
    
    public String toString2() {
        return  name + "\n" + surname + "\n" + gender + "\n" + birthDate + "\n" + bloodType + 
                "\n" + email + "\n" + password + "\n" + symptoms + "\n" + bitalino ;
    }

    
     public String toString3() {
        return  "Patient "+ patientId + ":"+ "\n" + "Name: "+ name + "\n" + "Surname: "+surname + "\n" + "Gender: "+ gender + "\n" + "Birth Date: "+ birthDate + "\n" + "Blood Type: "+ bloodType + 
                "\n" + "Email: "+ email + "\n" + "Symptoms: "+ symptoms + "\n" + "Bitalino Mac Adress: "+ bitalino ;
    }
    


}
