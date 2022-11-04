/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import BITalino.BITalino;
import BITalino.BITalinoException;
import BITalino.Frame;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.RemoteDevice;
import jdbc.JDBCUserManager;
import jpa.JPAUserManager;
import pojos.users.User;

/**
 *
 * @author carme
 */
public class Menu {
    
    private static JPAUserManager paman = new JPAUserManager();
    private static JDBCUserManager dbman = new JDBCUserManager();
    
    public static void main(String[] args) throws Exception {
		menuPrinicpal();
	}

	public static void menuPrinicpal() throws Exception {
		dbman.connect();
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
				dbman.disconnect();
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
				
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				addECG();
				break;
			case 0:
				Menu.menuPrinicpal();

			}

		}

	}
        public static Frame[] frame;
        
        private static void addECG(){
            BITalino bitalino = null;
        try {
            bitalino = new BITalino();
            // find devices
            //Only works on some OS
            Vector<RemoteDevice> devices = bitalino.findDevices();
            System.out.println(devices);

            //You need TO CHANGE THE MAC ADDRESS
            String macAddress = "98:D3:91:FD:69:49";
            int SamplingRate = 10;
            bitalino.open(macAddress, SamplingRate);

            // start acquisition on analog channels A2 and A6
            //If you want A1, A3 and A4 you should use {0,2,3}
            int[] channelsToAcquire = {1, 4};
            bitalino.start(channelsToAcquire);
             PrintWriter fichero = null;
             String nombre = InputOutput.getFilefromKeyboard();
              if (!nombre.endsWith(".txt")) {
                nombre = nombre + ".txt";}
                fichero = new PrintWriter(new FileWriter(nombre), true);
            //read 10 samples
            for (int j = 0; j < 10; j++) {

                //Read a block of 10 samples 
                frame = bitalino.read(10);

                //Print the samples
                int block_size = frame.length;
                for (int i = 0; i < frame.length; i++) {
                  
                    fichero.println((j * block_size + i) + " seq: " + frame[i].seq + " "
                            + frame[i].analog[0] + " "
                            + frame[i].analog[1] + " "
                            + frame[i].analog[2] + " ");

                }
                fichero.close();

            }
            //stop acquisition
            bitalino.stop();
        } catch (BITalinoException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                //close bluetooth connection
                if (bitalino != null) {
                    bitalino.close();
                }
            } catch (BITalinoException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        }
}
