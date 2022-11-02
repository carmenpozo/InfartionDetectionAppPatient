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
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlo
 */
public class Client {
    
   public static void main(String args[]) throws IOException {

        Socket socket = new Socket("localhost", 9000); // pedir localhost
        OutputStream outputStream = socket.getOutputStream();

        //File To Read from Bitalino
        File file = new File("BitalinoFilePath"); // pedir file path
        BufferedReader br = new BufferedReader(new FileReader(file));
        
        //send to the server data from the keyboard intstead of from the file:
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line;
        int lineInt;

        while ((line = br.readLine()) != null) { // reads the file and sends it to the server
            
            System.out.println(line);
            lineInt = Integer.parseInt(line);
            outputStream.write(lineInt); 
            outputStream.flush();
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        outputStream.flush();
        releaseResources(outputStream, br, socket);
        System.exit(0);
    }

    private static void releaseResources(OutputStream outputStream,
            BufferedReader br, Socket socket) {
        try {
            try {
                br.close();

            } catch (IOException ex) {
                Logger.getLogger(Client.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                outputStream.close();

            } catch (IOException ex) {
                Logger.getLogger(Client.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(Client.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
