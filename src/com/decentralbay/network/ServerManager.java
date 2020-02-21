package com.decentralbay.network;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.decentralbay.core.Config;


public class ServerManager implements Runnable {
	private static ServerManager ServerManager;
	//config vars
	private InetAddress serverInetAddr; 
	private int port;
	private int backlog;
	
	// server vars
	private ServerSocket serverSocket;
	public ArrayList<ServerConnection> connections = new ArrayList<ServerConnection>();
	private boolean _stop = false;
	private String ip = "";
	private static final Logger logger = Logger.getLogger(ServerManager.class.getName());
	
	public static void main(String[] args) throws UnknownHostException {
		ServerManager server = new ServerManager(Config.port, Config.backlog, Config.ip);
//		new Thread(() -> {
//			server.initServer();
//		}).start();
		
//		new Thread(() -> {
//			server.listenForInput();
//		}).start();
	}

	public static ServerManager getServerManagerSingleton(int port, int backlog, String ip) {
		if(ServerManager == null) {
			try {
				ServerManager = new ServerManager(port, backlog, ip);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ServerManager;
	}
	
	
	private ServerManager(int port, int backlog, String ip) throws UnknownHostException {
		this.port = port; 
		this.ip = ip;
		this.backlog = backlog;
		this.serverInetAddr = InetAddress.getByName(ip);
		new Thread(() -> {
			initServer();
		}).start();
	}

	
	private void initServer() {
		try {
			logger.log(Level.INFO, "New server listening on: " + Config.ip + " | Port: " + Config.port + " | Backlog: " + Config.backlog);
			serverSocket = new ServerSocket(this.port, this.backlog, this.serverInetAddr);
			bootstrapClients();
			while(!_stop) {
				Socket socket = serverSocket.accept();				
				BootstrapClients.addIp(String.valueOf(socket.getInetAddress()));
				
				startConnection(new ServerConnection(socket, this));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void listenForInput() {
		System.out.println("##############################################");
		Scanner console = new Scanner(System.in);

		while (true) {
			while (!console.hasNextLine()) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(this.connections.size());
			String input = console.nextLine();

			this.broadcastMessage(input);

			if (input.toLowerCase().equals("exit")) {
				break;
			}
		}
		console.close();
	}
	
	public void broadcastMessage(String message) {
		// TODO Auto-generated method stub
		for (int j = 0; j < this.connections.size(); j++) {
			if(!this.connections.get(j).getIp().equals(this.ip)) {
				this.connections.get(j).broadcastMessageToClient(message);
			}			
		}
	}
	
	
	private void startConnection(ServerConnection serverConnection) {
		logger.log(Level.INFO, "Attempting connection with node: " + serverConnection.getIp());
		if(!ifExists(serverConnection)) { //&& !serverConnection.getIp().equals("/127.0.0.1")) {
			Thread conn = new Thread(serverConnection);
			conn.start();
			
			this.connections.add(serverConnection);
			BootstrapClients.addIp(serverConnection.getIp());
		}
	}
	
	
	public void startConnection(String peer) {
		logger.log(Level.INFO, "Attempting connection with node: " + peer);
		ServerConnection serverConnection = new ServerConnection(peer, this.port, this);
		if(!ifExists(serverConnection)) { //&& !peer.equals("/127.0.0.1")) {
			Thread conn = new Thread(serverConnection);
			conn.start();
		
			this.connections.add(serverConnection);
			BootstrapClients.addIp(serverConnection.getIp());
		}
	}
	
	
	public boolean ifExists(ServerConnection serverConnection) {
		boolean exists = false;
		for (int i = 0; i < this.connections.size(); i++) {
			if(this.connections.get(i).getIp().equals(serverConnection.getIp()) && !serverConnection.getIp().equals("127.0.0.1")) {
				exists = true;
			}
		}
		return exists;
	}

	
	public void deleteConnection(String ip) throws Exception {
		logger.log(Level.INFO, "Current size of connections: " + this.connections.size());
		
		for (int j = 0; j < this.connections.size(); j++) {
			if(ip.toLowerCase().equals(this.connections.get(j).getIp())) {
				logger.log(Level.INFO, "Deleting connection: " + this.connections.get(j).getIp());
				this.connections.remove(j);
			}
		}
		this.connections.trimToSize();
	}
	
	
	public void deleteConnection(ServerConnection serverConnection) {
		logger.log(Level.INFO, "Current size of connections: " + this.connections.size());
		
		if (this.connections.size() > 0) {
			for (int j = 0; j < this.connections.size(); j++) {
				if (this.connections.get(j).toString().equals(serverConnection.toString())) {
					logger.log(Level.INFO, "Deleting connection: " + this.connections.get(j).getIp());
					this.connections.remove(j);
				}
			}

		}
		this.connections.trimToSize();
	}

	
	public void eraseConnection(ServerConnection serverConnection) {
		logger.log(Level.INFO, "Current size of connections: " + this.connections.size());
		
		if (this.connections.size() > 0) {
			for (int j = 0; j < this.connections.size(); j++) {
				if (this.connections.get(j).toString().equals(serverConnection.toString())) {
					try {
						logger.log(Level.INFO, "Erasing connection: " + this.connections.get(j).getIp());
						BootstrapClients.deleteIp(this.connections.get(j).getIp());
						this.connections.remove(j);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		this.connections.trimToSize();
	}

	
	public void eraseConnection(String ip) throws Exception {
		logger.log(Level.INFO, "Current size of connections: " + this.connections.size());
		
		if (this.connections.size() > 0) {
			for (int j = 0; j < this.connections.size(); j++) {
				if (this.connections.get(j).getIp().equals(ip)){
					try {
						logger.log(Level.INFO, "Erasing connection: " + this.connections.get(j).getIp());
						BootstrapClients.deleteIp(this.connections.get(j).getIp());
						this.connections.remove(j);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		this.connections.trimToSize();
	}
	
	
	private void bootstrapClients() {
		try {
			ArrayList<String> peers = PeerReader.getPool();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.log(Level.INFO, "Bootstraping clients!");
			for(String peer: peers) {
				if (!peer.equals(this.ip)) {
					startConnection(peer);					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		//this.initServer();
	}
}
