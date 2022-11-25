/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojos.Patient;

/**
 *
 * @author carlo
 */
public class Client {

    Socket socket;

    public Socket ConnectionWithServer(String ip) {
        socket = new Socket();
        try {
            socket = new Socket(ip, 9000);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return socket;
    }

    public void sendFileBitalino(File file, Socket socket) {
        try {
            OutputStream outputStream = socket.getOutputStream();

            //File To Read from Bitalino
            //File file = new File(filename); 
            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            String line;
            while ((line = br.readLine()) != null) { // reads the file and sends it to the server

                printWriter.println(line);

                System.out.println(line);

                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendPatient(Patient pat, Socket socket) {
        System.out.println("sendPatient");
        String patientSend = pat.toString2();
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(patientSend);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendLogin(String email, String password, Socket socket) {

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(email);
            //DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            //dataOutputStream.writeBytes(password);
            printWriter.println(password);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendOption(Socket socket, int id, int option) {
        OutputStream outputStream = null;
        //System.out.println("Send opt");
        try {
            outputStream = socket.getOutputStream();

            //And send it to the server
            outputStream.write(option);
            outputStream.write(id);
            outputStream.flush();
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendOpt(Socket socket, int option) {
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            //And send it to the server
            outputStream.write(option);
            outputStream.flush();
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Patient receivePatient(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //int id = bufferedReader.read();
        int id = Integer.parseInt(bufferedReader.readLine());
        System.out.println("id:" + id);
        String name = bufferedReader.readLine();
        System.out.println(name);
        String surname = bufferedReader.readLine();
        System.out.println(surname);
        String gender = bufferedReader.readLine();
        System.out.println(gender);
        Date birthDate = Date.valueOf(bufferedReader.readLine());
        System.out.println(birthDate);
        String bloodType = bufferedReader.readLine();
        String email = bufferedReader.readLine();
        byte[] password = bufferedReader.readLine().getBytes();
        String pw2 = new String(password, 0, password.length);
        System.out.println(pw2);
        String symptoms = bufferedReader.readLine();
        System.out.println(symptoms);
        String bitalino = bufferedReader.readLine();
        System.out.println(bitalino);

        Patient patient = new Patient(id, name, surname, gender, birthDate, bloodType, email, pw2, symptoms, bitalino);
        System.out.println(patient);

        bufferedReader.close();
        return patient;
    }

    public int receivepatientId(Socket socket) throws IOException {
        //BufferedReader bufferedReader = null;
        int id;
        InputStream inputstream = socket.getInputStream();
        DataInputStream dis = new DataInputStream(inputstream);
        //bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        id = dis.readInt();
        return id;
    }

    public String receivepatientFullNameandBitalino(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String fullName = bufferedReader.readLine();
        String bitalino = bufferedReader.readLine();
        System.out.println(fullName);
        System.out.println(bitalino);
        String info = fullName + "/" + bitalino;
        //bufferedReader.close();
        return info;
    }

    public String receiveFilesNames(Socket socket) throws IOException {
        String names = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
       //while ((line = bufferedReader.readLine())!= null) {
       for (int i = 0; i < 4; i++) {
            line = bufferedReader.readLine();
            //System.out.println(line);
            names = line + "//" + names;

        }
        //System.out.println("patient's files: " + names);
        return names;
    }

    private static void releaseResources(PrintWriter printWriter,
            BufferedReader br, Socket socket) {
        try {
            try {
                br.close();

            } catch (IOException ex) {
                Logger.getLogger(Client.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            printWriter.close();
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(Client.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exit() {
        releaseResources(socket);
    }

    private static void releaseResources(Socket socket) {

        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendEmail(Socket socket, String email) {
        PrintWriter printWriter;
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(email);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String receiveCheck(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String checkEmail = bufferedReader.readLine();
        return checkEmail;
    }

}
