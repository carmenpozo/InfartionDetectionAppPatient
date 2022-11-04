/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import BITalino.BITalino;
import BITalino.BITalinoException;
import BITalino.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.RemoteDevice;


/**
 *
 * @author carme
 */
public class Prueba {
    
   public static void main(String args[]) throws IOException {

        Socket socket = new Socket("10.60.84.251", 9000); // pedir localhost
        OutputStream outputStream = socket.getOutputStream();

        //File To Read from Bitalino
        File file = new File("C:\\Users\\carme\\OneDrive\\Documentos\\NetBeansProjects\\InfartionDetectionAppPatient\\Prueba.txt"); // pedir file path
        BufferedReader br = new BufferedReader(new FileReader(file));
        
        //send to the server data from the keyboard intstead of from the file:
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line;
        int lineInt;
      
         String[] datos = new String[6]; 
         int caract;
            int i = 0;
            char a;
            String dt = "";
            while ((caract = br.read()) != -1) {
                a = (char) caract;
                if (a != ' ') {
                    dt = dt + a;
                } else {
                    datos[i] = dt;
                    i++;
                    dt = "";
                    while (a != ';' || caract == -1) {
                        caract = br.read();
                        a = (char) caract;
                    }
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
                Logger.getLogger(Prueba.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                outputStream.close();

            } catch (IOException ex) {
                Logger.getLogger(Prueba.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(Prueba.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
