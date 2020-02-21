package com.decentralbay.network;


import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;

public class ClientManager {
	private String ip;
	private int port;
	private ServerManager serverManager;
	private ClientConnection clientConnection;
	private ArrayList<ClientConnection> connectionPool = new ArrayList<ClientConnection>();

	
	public ClientManager(ServerManager serverManager, String ip, int port) throws UnknownHostException {
		this.serverManager = serverManager;
		this.ip = ip;
		Thread server = new Thread(this.serverManager);
		server.start();
		System.out.println(this.ip + " " + String.valueOf(InetAddress.getLocalHost().getHostAddress()));
		if(this.ip.equalsIgnoreCase(String.valueOf(InetAddress.getLocalHost().getHostAddress()))) {
			try {
				Socket s = new Socket(ip, port);
				clientConnection = new ClientConnection(s, this);
				this.connectionPool.add(clientConnection);
				Thread conn = new Thread(clientConnection);
				conn.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void listenForInput() {
		Scanner console = new Scanner(System.in);

		while (true) {
			while (!console.hasNextLine()) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			String input = console.nextLine();

			clientConnection.broadcastMessageToServer(input);

			if (input.toLowerCase().equals("exit")) {
				break;
			}
		}
		clientConnection.close();
		console.close();
	}

	public String broadcastMessageToServer(String message) throws JSONException {
		clientConnection.broadcastMessageToServer(message);

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(clientConnection.getResponse());
		//closeClientConnection();
		return this.clientConnection.getResponse();
	}
	
	
	public void closeClientConnection() {
		this.clientConnection.close();
	}
}
