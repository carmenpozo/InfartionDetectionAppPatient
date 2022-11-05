/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;

import db.ifaces.DBManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carme
 */
public class JDBCUserManager implements DBManager {

    private Connection c  ;
    
    @Override
    public void connect() {
    try {
			// Open database connection
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/PatientApp.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error, database exception.");
		} catch (Exception e) {
			System.out.println("Error, couldn't connect to data based.");
			e.printStackTrace();
		}    
    }

    @Override
   public void disconnect() {
		try {
			// Close database connection
			c.close();
			System.out.println("Database connection close");
		} catch (SQLException e) {
			System.out.println("There was a problem while closing the database connection.");
			e.printStackTrace();
		}
	}

    @Override
    public String getPatientsFullNameByEmail(String email) {
        String fullName = null;
        try {
            String sql = "SELECT name, surname FROM patients WHERE email = ?";
            PreparedStatement prep = c.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            prep.setString(1, email);
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            fullName = name + " " + surname;
            prep.close();
            rs.close();  
            
        } catch (SQLException ex) {
            Logger.getLogger(JDBCUserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fullName;
    }

   
   
}

