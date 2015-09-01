/*******************************************************
 * Copyright (C) 2015 {Newaz Sharif Amit} <{newazsharifamit@yahoo.com}>
 * 
 * This file is part of {Multiple Client Chat Server}.
 * 
 * {Multiple Client Chat Server} can not be copied and/or distributed without the express
 * permission of {Newaz Sharif Amit}
 *******************************************************/


import java.io.*;
import java.net.*;
import java.util.*;


/**
* @author: newaz Sharif Amit
*/

public class Client implements Runnable{

    static Socket clientSocket = null;
    static PrintStream printStream = null;
    static DataInputStream dataInputStream = null;
    static BufferedReader inputLine = null;
    static boolean closed = false;
    
    public static void main(String[] cmd) {

    	Scanner scan = new Scanner(System.in);
	System.out.println("Enter Server Address");
	String serverName = scan.nextLine();
	System.out.println("Enter Specific Port Number To Connect with Server");
	int serverPort = scan.nextInt();
		

    	try {
    		clientSocket = new Socket(serverName, serverPort);
		System.out.println("Client Connected to" + clientSocket.getRemoteSocketAddress());
        	inputLine = new BufferedReader(new InputStreamReader(System.in));
        	printStream = new PrintStream(clientSocket.getOutputStream());
        	dataInputStream = new DataInputStream(clientSocket.getInputStream());

    	}catch (Exception ex) {
            ex.printStackTrace();
    	}

    	if (clientSocket != null && printStream != null && dataInputStream != null) {

    		try {
        		new Thread(new Client()).start();
        
        		while (!closed) {
                    	printStream.println(inputLine.readLine());
                	}

        		printStream.close();
        		dataInputStream.close();
        		clientSocket.close();
        	}catch (Exception e) {
               
            
        	}
    	}  

    }         
    
    	public void run() {
        
    		String responseLine;

    		try{ 
        		while ((responseLine = dataInputStream.readLine()) != null) {
    			System.out.println(responseLine);

        		if (responseLine.indexOf("Bye") != -1)
				break;
        		}
            		closed=true;
    		}catch (IOException e) {
       
    		}

    	}

}

