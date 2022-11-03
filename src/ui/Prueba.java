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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.RemoteDevice;


/**
 *
 * @author carme
 */
public class Prueba {
    
   public static void main(String[] args) throws Exception {
		addECG();
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
