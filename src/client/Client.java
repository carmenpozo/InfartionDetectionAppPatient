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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlo
 */
public class Client {
    
   public static void main(String args[]) throws IOException {

        Socket socket = new Socket("10.60.84.251", 9000); // pedir localhost
        OutputStream outputStream = socket.getOutputStream();

        //File To Read from Bitalino
        File file = new File("C:\\Users\\carme\\OneDrive\\Documentos\\NetBeansProjects\\InfartionDetectionAppPatient\\Prueba.txt"); // pedir file path
        BufferedReader br = new BufferedReader(new FileReader(file));
        PrintWriter printWriter = new PrintWriter(outputStream, true);
        String line;
        while ((line = br.readLine()) != null) { // reads the file and sends it to the server
            
            printWriter.write(line);
            
            System.out.println(line);
            
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        releaseResources(printWriter, br, socket);
        System.exit(0);
              
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
    
}

