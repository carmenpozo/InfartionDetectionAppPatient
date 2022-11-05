/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package db.ifaces;

import java.sql.SQLException;

/**
 *
 * @author carme
 */
public interface DBManager {
    
    public void connect(); // connect to the data base
    public void disconnect();//disconnect from the data base
    public String getPatientsFullNameByEmail(String email);
}
