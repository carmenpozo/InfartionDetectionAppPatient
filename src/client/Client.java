/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
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

    public static Socket ConnectionWithServer() {
        Socket socket = new Socket();
        try {
            socket = new Socket("10.60.84.251", 9000);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return socket;
    }

    public static void sendFileBitalino(String filename, Socket socket) {

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

            releaseResources(printWriter, br, socket);
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendPatient(Patient pat, Socket socket) {

        String patientSend = pat.toString();
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(patientSend);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static List receivePatient(Socket socket) {

        BufferedReader bufferedReader = null;
        List<String> atributes = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            atributes = new ArrayList();
            while ((line = bufferedReader.readLine()) != null) {

                atributes.add(line);
            }
            return atributes;
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResources(bufferedReader, socket);
        }
        return atributes;

    }

    public static List<String> receiveFilesNames(Socket socket) {
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
        } finally {
            releaseResources(bufferedReader, socket);
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
