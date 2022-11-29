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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.RemoteDevice;
import pojos.Patient;
import java.util.Scanner;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author carme
 */
public class Menu {

    private static Client client = new Client();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("Introduce the IP of the server you want to connect to: ");
            String ip = InputOutput.get_String();
            Socket socket = client.ConnectionWithServer(ip);
            while (socket.getInetAddress() == null) {
                System.out.println("Error, the connection to the server failed. \n Introduce another IP: ");
                ip = InputOutput.get_String();
                socket = client.ConnectionWithServer(ip);
            }
            while (true) {
                sc = new Scanner(System.in);
                System.out.println("\nWelcome to the Infarction Detection Application ");
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
                        System.out.println("Connection with the server succesfully closed");
                        System.exit(0);
                        break;
                    default:
                        break;
                }
            }
        } catch (SocketException e) {
            System.out.println("Connecion failed, the server has been closed");
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static void register(Socket socket) throws IOException, SocketException, NoSuchAlgorithmException {
        System.out.println("--- NEW ACCOUNT ---");
        System.out.println("Enter your email address:");
        String email = InputOutput.get_String();
        client.sendOpt(socket, 8);
        client.sendEmail(socket, email);
        String checkEmail = client.receiveCheck(socket);
        if (!checkEmail.equals("")) {
            System.out.println("The email introduced is already registered. \n");
            return;
        }
        System.out.println("Enter your password:");
        String password = InputOutput.get_String();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] hash2 = md.digest();
        String hash = new String(hash2, 0, hash2.length);

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
        System.out.println("----SYMPTOMS----\n");
        System.out.println("If you have any of this symptoms, introduce an x. \n");
        String symptoms = getSymptoms();

        System.out.println("MAC BITalino: ");
        String bitalino = InputOutput.get_String();

        Patient p = new Patient(name, surname, gender, birthdate, bt, email, hash, symptoms, bitalino);

        client.sendOpt(socket, 4);
        client.sendPatient(p, socket);
        System.out.print("\nAccount created.\n");
    }

    private static void login(Socket socket) throws SocketException, IOException {
        // Ask the user for an email
        System.out.println("Enter your email address: ");
        String email = InputOutput.get_String();
        client.sendOpt(socket, 8);
        client.sendEmail(socket, email);
        String checkEmail = client.receiveCheck(socket);
        if (checkEmail.equals("")) {
            System.out.println("The email introduced is not registered. \n");
            return;
        }
        // Ask the user for a password
        System.out.println("Enter your password:");
        String password = InputOutput.get_String();

        client.sendOpt(socket, 5);
        client.sendLogin(email, password, socket);
        int id = client.receivepatientId(socket);
        if (id == 0) {
            System.out.println("Wrong password.\n");
            return;
        }

        MenuPatient(id, socket);
    }

    private static void MenuPatient(int id, Socket socket) throws SocketException, IOException {
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
                    ViewInfo(socket, id);
                    break;
                }
                case 2:
                    PatientFiles(socket, id);
                    break;
                case 3:
                    addECG(socket, id);
                    break;
                case 0:
                    return;
                default:
                    break;
            }

        }

    }

    private static void ViewInfo(Socket socket, int id) throws IOException, SocketException {
        client.sendOption(socket, id, 1);
        Patient p = client.receivePatient(socket);
        System.out.println(p.toString3());
    }

    private static void PatientFiles(Socket socket, int id) throws IOException {

        client.sendOption(socket, id, 2);

        String names = client.receiveFilesNames(socket);

        String[] parts = names.split("//");
        List<String> files = new ArrayList();
        files = Arrays.asList(parts);

        if (files.size() == 0) {
            System.out.println("No files yet");
        } else {

            for (int i = 0; i < files.size(); i++) {
                System.out.println("File " + (i + 1) + ": " + files.get(i));
            }

            System.out.println("Choose the file number you want to see:");
            int num = InputOutput.get_int();
            String name = files.get(num - 1);
            File file = new File("files\\" + name);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    public static Frame[] frame;

    private static void addECG(Socket socket, int id) {
        BITalino bitalino = null;
        try {
            System.out.println("Performing the signal acquisition, please wait");
            bitalino = new BITalino();

            Vector<RemoteDevice> devices = bitalino.findDevices();
            System.out.println(devices);

            client.sendOption(socket, id, 7);
            String info = client.receivepatientFullNameandBitalino(socket);
            String[] info2 = info.split("/");
            String fullName = info2[0];
            String macAddress = info2[1];
            int SamplingRate = 10;
            bitalino.open(macAddress, SamplingRate);

            int[] channelsToAcquire = {1, 4};
            bitalino.start(channelsToAcquire);
            PrintWriter fichero = null;
            LocalDateTime current = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy.HH-mm-ss");
            String formattedDateTime = current.format(format);

            File file = new File("files\\patient" + id + "_" + formattedDateTime + ".txt");
            fichero = new PrintWriter(new FileWriter(file), true);

            fichero.println(id);
            fichero.println(formattedDateTime);
            fichero.println(fullName);

            //read 10 samples
            for (int j = 0; j < 15; j++) {

                //Read a block of 10 samples 
                frame = bitalino.read(10);

                fichero.println("size block: " + frame.length);

                //Print the samples
                int block_size = frame.length;
                for (int i = 0; i < frame.length; i++) {

                    fichero.println((j * block_size + i) + " seq: " + frame[i].seq + " "
                            + frame[i].analog[0] + " "
                            + frame[i].analog[1] + " "
                            + frame[i].analog[2] + " ");

                }
            }
            fichero.close();
            System.out.println("File saved");

            client.sendOpt(socket, 3);
            client.sendFileBitalino(file, socket);
            //stop acquisition
            bitalino.stop();
            try {
                //close bluetooth connection
                if (bitalino != null) {
                    bitalino.close();
                }

            } catch (BITalinoException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (BITalinoException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } /*finally {
            try {
                //close bluetooth connection
                if (bitalino != null) {
                    bitalino.close();
                }

            } catch (BITalinoException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/

    }

    private static String getSymptoms() {
        String chestpain = "";
        String fatigue = "";
        String dizziness = "";
        String sofbreath = "";
        String naussea = "";
        String vomits = "";
        String otherpains = "";
        System.out.println("Chest Pain : \n");
        String text = InputOutput.get_String();
        if (text.equals("x")) {
            chestpain = "chest pain, ";
        }
        System.out.println("Fatigue : \n");
        text = InputOutput.get_String();
        if (text.equals("x")) {
            fatigue = "fatigue, ";
        }
        System.out.println("Dizziness : \n");
        text = InputOutput.get_String();
        if (text.equals("x")) {
            dizziness = "dizziness, ";
        }
        System.out.println("Shortness of breath : \n");
        text = InputOutput.get_String();
        if (text.equals("x")) {
            sofbreath = "shortness of breath, ";
        }
        System.out.println("Naussea : \n");
        text = InputOutput.get_String();
        if (text.equals("x")) {
            naussea = "naussea, ";
        }
        System.out.println("Vomits : \n");
        text = InputOutput.get_String();
        if (text.equals("x")) {
            vomits = "vomits, ";
        }
        System.out.println("Other pains (jaw, neck, arm, back) : \n");
        text = InputOutput.get_String();
        if (text.equals("x")) {
            otherpains = "otherpains";
        }
        String symptoms = chestpain + fatigue + dizziness + sofbreath + naussea + vomits + otherpains;
        return symptoms;

    }

}
