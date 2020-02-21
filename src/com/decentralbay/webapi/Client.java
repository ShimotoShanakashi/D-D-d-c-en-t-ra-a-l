package com.decentralbay.webapi;

//Java implementation for a client 
//Save file as Client.java 

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.decentralbay.core.Config;

//Client class 
public class Client {
	private final static Logger logger = Logger.getLogger(Client.class.getName());

	public static String RequestToServer(String firstPart, String secondPart) throws IOException {
		String received = "null";
		
		try {
			// arraylist with all ip's
			ArrayList<String> ipPool = new ArrayList<String>();

			// getting ip pools
			// implement network client way, not this shit
			ipPool.add(Config.ip);

			for (int i = 0; i < ipPool.size(); i++) {
				InetAddress ip = InetAddress.getByName(ipPool.get(i));
				Socket s = new Socket(ip, 41111);
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				while (true) {
					String tosend = firstPart + "#" + secondPart;
					
					try {
						dos.writeUTF(tosend);
						received = dis.readUTF();
					} catch (Exception e){
						s.close();
						dos.close();
						dis.close();
					}
					

					if (tosend.equals("Exit")) {
						// System.out.println("Closing this connection : " + s);
						logger.log(Level.WARNING, "Web API: Closing this connection! ");
						s.close();
						logger.log(Level.WARNING, "Web API: Connection closed! ");
						break;
					}
					dos.writeUTF("Exit");
					break;
				}

				// closing resources
				// scn.close();
				dis.close();
				dos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return received;
	}
}