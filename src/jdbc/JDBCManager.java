/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc;
import java.sql.*;

/**
 *
 * @author mariadefarges
 */
public class JDBCManager {

    private Connection c = null;

    public JDBCManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:./db/PatientApp.db");
            c.createStatement().execute("PRAGMA foreign_keys=ON");
            System.out.println("Database connection opened.");

            this.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Libraries not loaded");
        }
    }

    public void disconnect() {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return c;
    }

    private void createTables() {
        // Create Tables
        try {
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE patients ("
                    + "	patientId INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "	name	  TEXT NOT NULL,"
                    + " surname   TEXT NOT NULL,"
                    + "	gender	  TEXT NOT NULL,"
                    + "	birthDate DATE NOT NULL,"                   
                    + " bloodType TEXT NOT NULL,"
                    + "	email     TEXT NOT NULL,"
                    + " password    TEXT NOT NULL,"
                    + "	diagnosis     TEXT NOT NULL,"
                    + "	ECG     TEXT NOT NULL,"
                    + " userId INTEGER REFERENCES users(id) ON DELETE CASCADE"
                    + ");";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE files ("
                    + "	fileId INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "	name	  TEXT NOT NULL,"
                    + "	patientId	  INTEGER REFERENCES patients(patientId) ON DELETE CASCADE,"
                    + ");";
            
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            if (!e.getMessage().contains("Already exists")) {
                e.printStackTrace();
            }
        }
    }
}
