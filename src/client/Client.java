/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojos.Patient;

/**
 *
 * @author carlo
 */
public class Client {

    Socket socket;

    public Socket ConnectionWithServer(String ip) throws IOException {
        socket = new Socket();
        socket = new Socket(ip, 9000);
        return socket;
    }

    public void sendFileBitalino(String data, Socket socket) throws IOException, SocketException {
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter printWriter = new PrintWriter(outputStream, true);
        printWriter.println(data);
        printWriter.println("stop");
        System.out.println(data);
    }

    public void sendPatient(Patient pat, Socket socket) throws IOException, SocketException {
        String patientSend = pat.toString2();
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(patientSend);
    }

    public void sendLogin(String email, String password, Socket socket) throws IOException, SocketException {

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(email);
        printWriter.println(password);
    }

    public void sendFileName(String name, Socket socket) throws IOException, SocketException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(name);
    }

    public void sendOption(Socket socket, int id, int option) throws IOException, SocketException {
        OutputStream outputStream = null;
        outputStream = socket.getOutputStream();
        //And send it to the server
        outputStream.write(option);
        outputStream.write(id);
      
    }

    public void sendOpt(Socket socket, int option) throws SocketException, IOException {
        OutputStream outputStream = null;
        outputStream = socket.getOutputStream();
        //And send it to the server
        outputStream.write(option);
        
    }

    public Patient receivePatient(Socket socket) throws IOException, SocketException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        int id = Integer.parseInt(bufferedReader.readLine());
        String name = bufferedReader.readLine();
        String surname = bufferedReader.readLine();
        String gender = bufferedReader.readLine();
        Date birthDate = Date.valueOf(bufferedReader.readLine());
        String bloodType = bufferedReader.readLine();
        String email = bufferedReader.readLine();
        byte[] password = bufferedReader.readLine().getBytes();
        String pw2 = new String(password, 0, password.length);
        String symptoms = bufferedReader.readLine();
        String bitalino = bufferedReader.readLine();

        Patient patient = new Patient(id, name, surname, gender, birthDate, bloodType, email, pw2, symptoms, bitalino);
        return patient;
    }

    public int receivepatientId(Socket socket) throws IOException, SocketException {
        int id;
        InputStream inputstream = socket.getInputStream();
        DataInputStream dis = new DataInputStream(inputstream);
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
        return info;
    }

    public String receiveFilesNames(Socket socket) throws IOException, SocketException {
        String names = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while (!((line = bufferedReader.readLine()).equals("stop"))) {
            names = line + "//" + names;

        }
        return names;
    }

    public String receiveFiles(Socket socket) throws IOException {
        String file = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while (!((line = bufferedReader.readLine()).equals("stop"))) {
            file = file + "\n" + line;

        }
        return file;
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

    public void sendEmail(Socket socket, String email) throws IOException, SocketException {
        PrintWriter printWriter;
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(email);

    }

    public String receiveCheck(Socket socket) throws IOException, SocketException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String checkEmail = bufferedReader.readLine();
        return checkEmail;
    }

}
