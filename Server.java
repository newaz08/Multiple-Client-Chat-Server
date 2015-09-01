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

public class Server{

	static Socket clientSocket = null;
	static ServerSocket serverSocket = null;
	
	public static void main(String []cmd) {

		int portNumber = Integer.parseInt(cmd[0]);
		List<ClientHandler> clientList = new ArrayList<ClientHandler>();           

		try {
			serverSocket = new ServerSocket(portNumber);
        	}catch (IOException e) {

        	}
    		while(true){

        		try {
        			clientSocket = serverSocket.accept();
				ClientHandler clientHandler = new ClientHandler(clientSocket,clientList);
				clientList.add(clientHandler);
				clientHandler.start();
			}catch (IOException e) {

        		}
    		}
    	}

} 

