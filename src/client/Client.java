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

    public Socket ConnectionWithServer(String ip) {
        Socket socket = new Socket();
        try {
            socket = new Socket(ip, 9000);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return socket;
    }

    public void sendFileBitalino(String filename, Socket socket) {

        try {
            OutputStream outputStream = socket.getOutputStream();

            //File To Read from Bitalino
            File file = new File("filename"); // pedir file path
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
            System.out.println(option);
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

    public Patient receivePatient(Socket socket) throws IOException{
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
        LocalDate d = LocalDate.parse(bufferedReader.readLine(), formatter);
        Date birthDate = Date.valueOf(d);//Date.valueOf(bufferedReader.readLine());
        System.out.println(birthDate);
        String bloodType = bufferedReader.readLine();
        String email = bufferedReader.readLine();
        byte[] password = bufferedReader.readLine().getBytes();
        System.out.println(password);
        String symptoms = bufferedReader.readLine();
        System.out.println(symptoms);
        String bitalino = bufferedReader.readLine();
        System.out.println(bitalino);

        Patient patient = new Patient(id, name, surname, gender, birthDate, bloodType, email, password, symptoms, bitalino);
        System.out.println(patient);
        return patient;
        
       /* System.out.println("okok");
        BufferedReader bufferedReader = null;
        List<String> atributes = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            atributes = new ArrayList();
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                atributes.add(line);
            }
            System.out.println(atributes);
            return atributes;
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return atributes;*/

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

    public List<String> receiveFilesNames(Socket socket) {
        BufferedReader bufferedReader = null;
        List<String> names = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            names = new ArrayList();
            while ((line = bufferedReader.readLine()) != null) {

                names.add(line);
            }
            return names;
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    private static void releaseResources(BufferedReader bufferedReader, Socket socket) {
        try {
            bufferedReader.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
