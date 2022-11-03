/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.in;

/**
 *
 * @author carme
 */
public class InputOutput {
    
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
    public static int get_int() {

		int id = 0;
		try {
			id = Integer.parseInt(in.readLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static String get_String() {

		String a = null;
		try {
			a = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return a;
	}

	public static Float get_Float() {

		Float f = null;
		try {
			f = Float.parseFloat(in.readLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f;
	}

	public static String getNamefromKeyboard() {
		String a = null;
		try {
			System.out.println("Enter the patient's name and surname:");
			a = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return a;
	}
        public static String getFilefromKeyboard() {
		String a = null;
		try {
			System.out.println("Enter the Name of the File:");
			a = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return a;
	}
}
