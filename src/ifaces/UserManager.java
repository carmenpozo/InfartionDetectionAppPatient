/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifaces;

import pojos.User;

/**
 *
 * @author carme
 */
public interface UserManager {
    
    public void connect();//connect to the data base 
	public void disconnect(); //disconnect
	public void newUser(User u); // create a new user
	public User checkPassword(String email, String password); // check if the password is correct 
        public User checkEmail (String email);
        public void updateUser(User u, byte[] password);
            
}
