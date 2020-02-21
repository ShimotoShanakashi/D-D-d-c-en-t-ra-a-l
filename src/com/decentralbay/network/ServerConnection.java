package com.decentralbay.network;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;

import com.decentralbay.core.Config;




public class ServerConnection implements Runnable {
	private Socket socket = null;
	private ServerManager serverManager;
	private DataInputStream din;
	private DataOutputStream dout;
	private volatile boolean _stop = false;
	private String ip;
	private int port;
	private static final Logger LOGGER = Logger.getLogger(ServerConnection.class.getName());
	

	public ServerConnection(Socket socket, ServerManager serverManager) {
		this.socket = socket;
		this.ip = String.valueOf(this.socket.getInetAddress());
		this.serverManager = serverManager;
		LOGGER.log(Level.INFO, "ServerConnection - (NEW)Opened:" + socket);
	}

	public ServerConnection(String ip, int port, ServerManager serverManager) {
		this.serverManager = serverManager;
		this.ip = ip;
		this.port = port;
		LOGGER.log(Level.INFO, "ServerConnection - (NEW)Opened:[ip:" + this.ip + "|port:" + this.port + "]");
	}

	public void broadcastMessageToClients(String message) {
		for (int index = 0; index < this.serverManager.connections.size(); index++) {
			if (!this.serverManager.connections.get(index).toString().equals(this.toString())) {
				try {
					this.serverManager.connections.get(index).broadcastMessageToClient(message);	
				} catch (Exception eException) {
					try {
						this.close(false);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			}
		}
	}

	public void broadcastMessageToClient(String message) {
		if (this.dout != null) {
			try {
				this.dout.writeUTF(message);
				this.dout.flush();
			} catch (Exception e) {
				try {
					this.close(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}				
		}
	}

	public String getIp() {
		return this.ip;
	}

	public String getClients() {
		String x = "";
		for (int index = 0; index < this.serverManager.connections.size(); index++) {
			if (index == this.serverManager.connections.size() - 1) {
				x += this.serverManager.connections.get(index).getIp();
			} else {
				x += this.serverManager.connections.get(index).getIp() + ",";
			}
		}

		return x;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (socket == null) {
			try {
				this.socket = new Socket(this.ip, this.port);
			} catch (ConnectException connException) {
				try {
					LOGGER.log(Level.FINER, "Connect exception timed out, erasing socket - " + this.socket);
					
					if(Config.eraseconnections == true) {
						this.close(true);
					} else {
						this.close(false);
					}					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				try {
					LOGGER.log(Level.FINER, "Connect exception IOError, deleting socket - " + this.socket);
					this.close(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this._stop = true;
				e.printStackTrace();
			}
		}

		if (this.socket != null) {
			try {
				this.din = new DataInputStream(this.socket.getInputStream());
				this.dout = new DataOutputStream(this.socket.getOutputStream());

				this.broadcastMessageToClients("ping");
				while (!_stop) {
					while (din.available() == 0) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					String receivedMessage = din.readUTF();
					LOGGER.log(Level.INFO, "Received message on client side: " + receivedMessage);
					CommandHandler.handleMessage(receivedMessage, this, this.serverManager);
				}

				try {
					LOGGER.log(Level.FINER, "Connect exception paused, deleting socket - " + this.socket);
					this.close(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public void close(boolean deleteFromFile) throws Exception {
		try {
			this._stop = true;
			if (this.socket != null && this.din != null && this.dout != null) {
				this.din.close();
				this.dout.close();
				this.socket.close();
			}

			if(deleteFromFile == true) {
				this.serverManager.eraseConnection(this);
			} else {
				this.serverManager.deleteConnection(this);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
