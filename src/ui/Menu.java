/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.security.MessageDigest;
import jpa.JPAUserManager;
import pojos.users.User;

/**
 *
 * @author carme
 */
public class Menu {
    
    private static JPAUserManager paman = new JPAUserManager();
    
    public static void main(String[] args) throws Exception {
		menuPrinicpal();
	}

	public static void menuPrinicpal() throws Exception {
		//dbman.connect();
		paman.connect();

		while (true) {
			System.out.println("\nWELCOME! ");
			System.out.println("\nChoose an option : ");
			System.out.println("1.Register ");
			System.out.println("2.Log in");
			System.out.println("0.EXIT. ");

			int opcion = InputOutput.get_int();

			switch (opcion) {
			case 1:
				register();
				break;
			case 2:
				login();
			case 0:
				//dbman.disconnect();
				paman.disconnect();
				System.exit(0);
				break;
			default:
				break;
			}
		}
	}

	private static void register() throws Exception {

		System.out.println("Enter your email address:");
		String email = InputOutput.get_String();

		System.out.println("Enter your password:");
		String password = InputOutput.get_String();
		
		// Generate the hash
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hash = md.digest();
		User user = new User(email, hash);
		paman.newUser(user);
	}

	private static void login() throws Exception {
		// Ask the user for an email
		System.out.println("Enter your email address: ");
		String email = InputOutput.get_String();
		// Ask the user for a password
		System.out.println("Enter your password:");
		String password = InputOutput.get_String();
		User user = paman.checkPassword(email, password);
		if (user == null) {
			System.out.println("Wrong email or password");
			return;
		} else {
                    MenuPatient();
                    
		}

		// Check the type of the user and redirect her to the proper menu
	}
        
        private static void MenuPatient() throws Exception {
		while (true) {
			System.out.println("\n1.View my information. ");
			System.out.println("2.View my files ");
			System.out.println("3.View my diagnosis");
			System.out.println("4.Perform a new Electorcardiogram");;
			System.out.println("0.EXIT. ");
			System.out.println("\nChoose an option : ");

			int opcion = InputOutput.get_int();
			switch (opcion) {
			case 1:
				newpat();
				break;
			case 2:
				newpatXML();
				break;
			case 3:
				createHtmlpat();
				break;
			case 4:
				addcovid();
				break;
			case 0:
				Menu.menuPrinicpal();

			}

		}

	}
}
