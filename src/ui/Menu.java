/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import BITalino.BITalino;
import BITalino.BITalinoException;
import BITalino.Frame;
import client.Client;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.RemoteDevice;
import pojos.Patient;
import java.util.Scanner;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author carme
 */
public class Menu {

    // private static JPAUserManager paman = new JPAUserManager();
    // private static JDBCUserManager dbman = new JDBCUserManager();
    private static Client client = new Client();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        menuPrinicpal();
    }

    public static void menuPrinicpal() throws Exception {
        System.out.println("Introduce the IP of the server you want to connect to: ");
        String ip = InputOutput.get_String();
        Socket socket = client.ConnectionWithServer(ip);
        while (socket.getInetAddress() == null){
            System.out.println("Error, the connection to the server failed. \n Introduce another IP: ");
            ip = InputOutput.get_String();
            socket = client.ConnectionWithServer(ip);
        }
        sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nWELCOME! ");
            System.out.println("\nChoose an option : ");
            System.out.println("1.Register ");
            System.out.println("2.Log in");
            //System.out.println("3.Change password");
            System.out.println("0.EXIT. ");

            int opcion = InputOutput.get_int();

            switch (opcion) {
                case 1:
                    register(socket);
                    break;
                case 2:
                    login(socket);
                    break;
                case 0:
                    client.sendOpt(socket, 6);
                    client.exit();
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    private static void register(Socket socket) throws Exception {
        System.out.println("--- NEW ACCOUNT ---");
        System.out.println("Enter your email address:");
        String email = InputOutput.get_String();
        System.out.println("Enter your password:");
        String password = InputOutput.get_String();
        // Generate the hash
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] hash2 = md.digest();
        String hash =  new String(hash2, 0, hash2.length);

        System.out.println("Name: ");
        String name = InputOutput.get_String();
        System.out.println("Surname: ");
        String surname = InputOutput.get_String();
        System.out.println("Gender: ");
        String gender = InputOutput.get_String();
        if (gender.equalsIgnoreCase("male")) {
            gender = "Male";
        }
        if (gender.equalsIgnoreCase("female")) {
            gender = "Female";
        }
        while (!(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female"))) {
            System.out.print("Introduce a valid gender (male/female): ");
            gender = InputOutput.get_String();
        }

        System.out.println("Date of birth (year-month-day): ");
        Date birthdate = null;
        try {
            birthdate = Date.valueOf(InputOutput.get_String());
        } catch (Exception e1) {
            birthdate = null;
        }
        while (birthdate == null) {
            System.out.println("Please introduce a valid date: ");
            try {
                birthdate = Date.valueOf(InputOutput.get_String());
            } catch (Exception e2) {
                birthdate = null;
            }
        }
        System.out.println("Bloodtype, just the letter (A, B, AB, O): ");
        String bt = InputOutput.get_String();

        if (bt.equals("a")) {
            bt = "A";
        }
        if (bt.equals("b")) {
            bt = "B";
        }
        if (bt.equals("o")) {
            bt = "O";
        }
        if (bt.equals("ab")) {
            bt = "AB";
        }
        while (!(bt.equalsIgnoreCase("a") || bt.equalsIgnoreCase("b") || bt.equalsIgnoreCase("o") || bt.equalsIgnoreCase("ab"))) {
            System.out.print("Introduce a valid bloodtype (A, B, AB, O):  ");
            bt = InputOutput.get_String();
        }

        System.out.println("Symptoms: ");
        String symptoms = InputOutput.get_String();
        System.out.println("MAC BITalino: ");
        String bitalino = InputOutput.get_String();

        Patient p = new Patient(name, surname, gender, birthdate, bt, email, hash, symptoms, bitalino);
        
        client.sendOpt(socket, 4);
        client.sendPatient(p, socket);
        System.out.print("\nAccount created.\n");
    }

    private static void login(Socket socket) throws Exception {
        // Ask the user for an email
        System.out.println("Enter your email address: ");
        String email = InputOutput.get_String();

        // Ask the user for a password
        System.out.println("Enter your password:");
        String password = InputOutput.get_String();
        System.out.println("password: "+password);
        //MessageDigest md = MessageDigest.getInstance("MD5");
	//md.update(password.getBytes());
	//byte[] pw = md.digest();
    
        client.sendOpt(socket, 5);
        client.sendLogin(email, password, socket);
        //List pat = client.receivePatient(socket); 
        int id = client.receivepatientId(socket);
        System.out.println("Patient id: " + id);
        //Patient p = new Patient(id,email,pw);
        //p.setPatientId((Integer) pat.get(0));
        MenuPatient(id, socket);
    }

    
    /*private static void changePassword() {
        sc = new Scanner(System.in);
        try {
            System.out.println("Email:");
            String email = InputOutput.get_String();
            System.out.println("Password:");
            String password = InputOutput.get_String();
            Patient patient = pm.checkPassword(email, password);
            System.out.println("Introduce the new password: ");
            String newPassword1 = InputOutput.get_String();
            System.out.println("Confirm your new password: ");
            String newPassword2 = InputOutput.get_String();
            if (newPassword1.equals(newPassword2)) {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(newPassword1.getBytes());
                byte[] hash = md.digest();
                pm.UpdatePatient(patient, hash);
                System.out.println("Password updated");
            } else {
                System.out.println("The passwords don't match");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/

    private static void MenuPatient(int id, Socket socket) throws Exception {
        sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1.View my information. ");
            System.out.println("2.View my files ");
            System.out.println("3.Perform a new Bitalino");
            System.out.println("0.Return ");
            System.out.println("\nChoose an option : ");

            int opcion = InputOutput.get_int();
            switch (opcion) {
                case 1: {
                    System.out.println("ok");
                    ViewInfo(socket, id);
                    break;
                }
                case 2:
                    PatientFiles(socket, id);
                    break;
                case 3:
                    //addECG(socket, p);
                    break;
                case 0:
                    Menu.menuPrinicpal();
                    break;
                default:
                    break;
            }

        }

    }

    private static void ViewInfo(Socket socket, int id){
        //int id = p.getPatientId();
        client.sendOption(socket, id, 1);
        //List information = client.receivePatient(socket);
        try{
            Patient p = client.receivePatient(socket);
            System.out.println(p);
        }catch(IOException e){
            e.printStackTrace();
        }
        
        

    }

    private static void PatientFiles(Socket socket, int id) {
        //int id = p.getPatientId();
        client.sendOption(socket, id, 2);
        List names = client.receiveFilesNames(socket);
        System.out.println(names);

    }

    public static Frame[] frame;

    private static void addECG(Socket socket, Patient p) {
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
            //fichero.println(patient.getPatientId());
            fichero.println(java.time.LocalDateTime.now());
            //fichero.println(patient.getName());
            //fichero.println(patient.getSurname());
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
                client.sendOpt(socket,3);
                client.sendFileBitalino(nombre, socket);
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

