package com.decentralbay.network;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class ClientConnection implements Runnable {
	private Socket socket;
	private ClientManager clientManager;
	private DataInputStream din;
	private DataOutputStream dout;
	private ArrayList<JSONObject> actualResponse = new ArrayList<JSONObject>();
	private ArrayList<JSONObject> responses = new ArrayList<JSONObject>();
	private boolean _stop = false;
	private String strResponse = "";
	


	public ClientConnection(Socket socket, ClientManager clientManager)  {
		this.socket = socket;
		this.clientManager = clientManager;
	}
	
	
	@Override
	public void run() {
		try {
			this.din = new DataInputStream(this.socket.getInputStream());
			this.dout = new DataOutputStream(this.socket.getOutputStream());
			
			while(!this._stop) {
				try {
					while(this.din.available() == 0) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					
					// receive reply		
					String reply = din.readUTF();
					this.strResponse = reply;
					addResponse(reply);
					//System.out.println("Mensaje recibido en el lado cliente: " + reply);
				} catch (IOException | JSONException e2) {
					try {
						//this.close();
						Thread.currentThread().interrupt();						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					e2.printStackTrace();
				}
			}
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}		
	}

	
	private void addResponse(String response) throws JSONException {
		if(this.actualResponse.size() == 0) {
			JSONObject x = new JSONObject();
			this.actualResponse.add(x.put("response#" + this.responses.size() + 1, response));			
		} else {
			this.responses.add(this.actualResponse.get(0));
			this.actualResponse = new ArrayList<JSONObject>();
			JSONObject x = new JSONObject();
			this.actualResponse.add(x.put("response#" + this.responses.size() + 1, response));			
		}
	}
	
	
	public void close() {
		try {
			if(this.socket != null && this.dout != null) {
				this.broadcastMessageToServer("exit");	
			}			
			this._stop = true;
			this.din.close();
			this.dout.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void broadcastMessageToServer(String message) {
		// TODO Auto-generated method stub
		while (this.dout == null || this.socket == null) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
			
		try {
			this.dout.writeUTF(message);
			this.dout.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String getResponse() throws JSONException {
		// TODO Auto-generated method stub
		int counter = 0;
		int limit = 4000;
		
		while(this.strResponse.isEmpty() && counter < limit) {
			try {
				Thread.sleep(1);
				counter++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.strResponse;
	}
}
