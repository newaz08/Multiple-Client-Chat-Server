/*******************************************************
 * Copyright (C) 2015 {Newaz Sharif Amit} <{newazsharifamit@yahoo.com}>
 * 
 * This file is part of {Multiple Client Chat Server}.
 * 
 * {Multiple Client Chat Server} can not be copied and/or distributed without the express
 * permission of {Newaz Sharif Amit}
 *******************************************************/

import java.io.*;
import java.util.Scanner;

/**
* @author: newaz Sharif Amit
*/

public class UserVerify {

	private static UserVerify userVerify = null;
	FileReader fileReader = null;
	static PrintStream printStream = null;
	String record = null;
	boolean isFound = false;
	String name = null;
	String password = null;
	
	private UserVerify() {

	}

	public static UserVerify getInstance() {

		if(userVerify == null) {

			userVerify = new UserVerify();
		}
		
		return userVerify;
	}

	public boolean isVerify(String name, String password) {
		this.name = name;
		this.password = password;

		try {
               		FileInputStream fstream = new FileInputStream("login.txt");
               		DataInputStream inputStream = new DataInputStream(fstream);
               		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
               		String temp;

               		while ((temp = bufferedReader.readLine()) != null) {
                  		String[] splitted = temp.split(" ");
            			if (name.equals(splitted[0])&&password.equals(splitted[1])){
           				isFound = true;
             			}

				else if(isFound == false)
					{addUser();}
                                    
               		}

               		

               		inputStream.close();
             	}catch(Exception ex) {
               		ex.printStackTrace();
                }

		return isFound;
	}

	public static void addUser() {

        	String new_uname = null;
        	String new_pw = null;
        	
        	String choice = null;

        	Scanner console = new Scanner(System.in);
        	printStream.println("Do you want to register? y/n:");
        	choice = console.next(); 
   
    		while (!(choice.equalsIgnoreCase("y")||choice.equalsIgnoreCase("n"))) {
        		printStream.print("Try again! y/n:");
         		choice = console.next();    
    		}

    		if(choice.equals("y")){
        		printStream.print("Enter username:");
         		new_uname = console.next();
        		printStream.print("Enter password:");
         		new_pw = console.next();
        		

         		try {
         			BufferedWriter out = new BufferedWriter(new FileWriter("login.txt", true));
				//out.newLine();
            			out.write(new_uname+" "+new_pw);
				out.newLine();
            			printStream.print("Thanks for registering!");
				
           			out.close();
             		} catch (Exception e) {
               			System.err.println(e);
             		}
          
		}

		else
			System.exit(0);
    	}

}