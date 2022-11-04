/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;

import db.ifaces.DBManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

   
  
}

