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

class ClientHandler extends Thread{
    
	DataInputStream dataInputStream = null;
	String line;
	String destClient="";
	String name;
	String password;
	PrintStream printStream = null;
	Socket clientSocket = null;       
	List<ClientHandler> clientList;
	String clientIdentity; 
    
    	public ClientHandler(Socket clientSocket, List<ClientHandler> clientList){
    		this.clientSocket=clientSocket;
        	this.clientList=clientList;
    	}
    
	public void selectDestClient() throws IOException {
		int h=0;
		printStream.println("Total Members online in Chat Server : "+clientList.size());
                printStream.println("With whom you want to chat ?");

    		if(clientList.size()>1) {
        		for(int i=0; i<clientList.size(); i++) {
    				if(clientList.get(i)!=this) {
            				this.printStream.println(" * " +(clientList.get(i)).clientIdentity);
                                }
				if(clientList.indexOf(clientList.get(i))==(clientList.size()-1)) {
					printStream.println("\nPlease Enter the name from above list to chat : ");
					while(true) {
    						destClient = dataInputStream.readLine();

						if(destClient.equalsIgnoreCase("sendall")) {
									
							printStream.println("\nYou are connected with other client....Start enjoying this chat service.\n");
								return;
						}						

						for(ClientHandler ct:clientList) {
							if(destClient.equalsIgnoreCase(ct.clientIdentity) && ct!=clientList) {
								printStream.println("\nYou are connected with "+destClient+"....Start enjoying this chat service.\n");
								return;
							}
						}
						printStream.println("\nInvalid destination.There is NO user with name "+destClient+" \n Please Re-enter : ");
						continue;

						
					} 
				}
    			}
    		}else {
            		this.printStream.println("\n- Currently no other user in for Chatting.\n- You are the first one.Wait for some clients....");
    		}


	}


	/*public void selectAllClient() throws IOException {
		int h=0;
		printStream.println("Total Members online in Chat Server : "+clientList.size());
                

    		if(clientList.size()>1) {
        		for(int i=0; i<clientList.size(); i++) {
    				if(clientList.get(i)!=this) {
            				this.printStream.println(" * " +(clientList.get(i)).clientIdentity);
                                }
				
					
					while(true) {
    						destClient = dataInputStream.readLine();

						for(ClientHandler ct:clientList) {
							
								printStream.println("\nYou are connected with other client....Start enjoying this chat service.\n");
								return;
							
						}
						
					}
				
    			}
    		}else {
            		this.printStream.println("\n- Currently no other user in for Chatting.\n- You are the first one.Wait for some clients....");
    		}


	}*/

	public void run() {
    		try{
        		dataInputStream = new DataInputStream(clientSocket.getInputStream());
        		printStream = new PrintStream(clientSocket.getOutputStream());
        		printStream.println("Enter your name.");
        		name = dataInputStream.readLine();
                        clientIdentity = name;
			printStream.println("Enter your password");
			password = dataInputStream.readLine();
			
			boolean status	= UserVerify.getInstance().isVerify(name,password);

			if(status) {
        			printStream.println("Welcome "+name+" to this chatting Server\n");
				printStream.println("Instructions :\n To Sign-Out enter quit in a new line\n");
				printStream.println("To view List of Chat Members enter view amd follow the instructions.\n");
				printStream.println("To Send Broadcast message type sendAll\n");

             			for(int i=0; i<clientList.size(); i++) {
    					if(clientList.get(i)!=this) {
                                		(clientList.get(i)).printStream.println("A new user "+name+" entered into chat Server \n");
                                 	}
				}
				selectDestClient();
        			while (true) {
        				line = dataInputStream.readLine();

					if(line.startsWith("view")) {
						selectDestClient();
						continue;
					}

                			if(line.startsWith("quit")) break; 

    					if(clientList.size()>1) {
        					for(int i=0; i<clientList.size(); i++) {
							if((clientList.get(i))!=this) {
        							if((clientList.get(i)).clientIdentity.equalsIgnoreCase(destClient)) {
                                       					(clientList.get(i)).printStream.println("\n <From "+this.clientIdentity+"> "+line); 
                							break;
            							}

							}

           					}
					}

					if(line.startsWith("sendall")) {
						selectDestClient();
						continue;
					}

					if(clientList.size()>1 && destClient.startsWith("sendall")) {

						//selectAllClient();
						//if(clientList.size()>1) {
							for(int i=0; i<clientList.size(); i++) {
								(clientList.get(i)).printStream.println("\n <From "+this.clientIdentity+"> "+line); 
                							
						
							}
						//}
					}
					
				}

          		}

			else {
				printStream.println("You are not registered User To continue you have to register");
				clientList.remove(clientIdentity);
				//UserVerify.getInstance().addUser();
			}

    			if(clientList.size()>1) {
       				for(int i=0; i<clientList.size(); i++) {
        				if(clientList.get(i)!=this)
                           			(clientList.get(i)).printStream.println("The user "+name+" left the chat Server" ); 

    				}
			}
        		printStream.println(" Bye "+name+" "); 
			clientList.remove(this);
        		dataInputStream.close();
        		printStream.close();
        		clientSocket.close();

    		} catch(IOException ex){
			ex.printStackTrace();
		}
   	 }

}