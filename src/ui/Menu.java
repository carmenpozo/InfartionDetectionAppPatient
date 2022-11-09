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
import pojos.Patient;
import pojos.User;
import java.util.Scanner;
import jdbc.JDBCManager;
import jdbc.JDBCPatientManager;

/**
 *
 * @author carme
 */
public class Menu {

    private static JPAUserManager paman = new JPAUserManager();
    private static JDBCUserManager dbman = new JDBCUserManager();
    private static JDBCManager m = new JDBCManager();
    private static JDBCPatientManager pm = new JDBCPatientManager(m);

    static Scanner sc = new Scanner(System.in);

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
            System.out.println("3.Change password");
            System.out.println("0.EXIT. ");

            int opcion = InputOutput.get_int();

            switch (opcion) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                case 3:
                    changePassword();
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
        System.out.println("--- NEW ACCOUNT ---");
        System.out.println("Enter your email address:");
        String email = InputOutput.get_String();

        while (paman.checkEmail(email) != null) {
            System.out.println("The email is already registered. Introduce another email: ");
            email = InputOutput.get_String();
        }

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
        while (paman.checkEmail(email) == null) {
            System.out.println("The email is not registered. Introduce another email: ");
            email = InputOutput.get_String();
        }

        // Ask the user for a password
        System.out.println("Enter your password:");
        String password = InputOutput.get_String();
        while (paman.checkPassword(email, password) == null) {
            System.out.println("The password introduced is not valid, try again.");
            password = InputOutput.get_String();
        }
        User u = paman.checkPassword(email, password);

        MenuPatient(u);
    }

    // Check the type of the user and redirect her to the proper menu
    private static void changePassword() {
        sc = new Scanner(System.in);
        try {
            System.out.println("Username:");
            String username = InputOutput.get_String();
            System.out.println("Password:");
            String password = InputOutput.get_String();
            User user = paman.checkPassword(username, password);
            System.out.println("Introduce the new password: ");
            String newPassword1 = InputOutput.get_String();
            System.out.println("Confirm your new password: ");
            String newPassword2 = InputOutput.get_String();
            if (newPassword1.equals(newPassword2)) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(newPassword1.getBytes());
                byte[] hash = md.digest();
                paman.updateUser(user, hash);
                System.out.println("Password updated");
            } else {
                System.out.println("The passwords don't match");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void MenuPatient(User user) throws Exception {
        sc = new Scanner(System.in);
        Patient patient = pm.getPatientByUserId(user.getId());
        while (true) {
            System.out.println("\n1.View my information. ");
            System.out.println("2.View my files ");
            System.out.println("3.Perform a new Electorcardiogram");
            System.out.println("3.Perform an Accelerometer Test");
            System.out.println("0.Return ");
            System.out.println("\nChoose an option : ");

            int opcion = InputOutput.get_int();
            switch (opcion) {
                case 1:{
                    System.out.println("\n----- Mr/Mrs " + patient.getName() + " " + patient.getSurname() + " profile -----\n");
                    System.out.println(patient);
                    System.out.println("\n"); 
                    System.out.println("0. Return");
                    int choice = InputOutput.get_int();
                    if(choice == 0) return;
                    break;
                }
                case 2:
                    PatientFiles();
                    break;
                case 3:
                    addECG(patient);
                    break;
                case 4:
                    addAccelerometer(patient);
                    break;
                case 0:
                    Menu.menuPrinicpal();
                    break;
                default:
                    break;
            }

        }

    }

    private static void PatientFiles() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static Frame[] frame;

    private static void addECG(Patient patient) {
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
                nombre = nombre + ".txt";
            }
            fichero = new PrintWriter(new FileWriter(nombre), true);
            fichero.print(java.time.LocalDateTime.now());
            fichero.print(patient.getName());
            fichero.print(patient.getSurname());
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

    private static void addAccelerometer(Patient patient) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
