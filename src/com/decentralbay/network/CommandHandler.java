package com.decentralbay.network;


import java.io.IOException;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.iq80.leveldb.DBException;
import org.json.JSONException;
import org.json.JSONObject;
import com.decentralbay.core.Blockchain;
import com.decentralbay.utils.StringToJSONObject;

public class CommandHandler {
	final static String jsonStringBlock = "{\"timeStamp\":1579455292516,\"previousHash\":\"previoushash\",\"serverWinner\":\"192.168.1.41\",\"bits\":\"034b11a\",\"merkleRoot\":\"4769a0d555607a678a4228c204e9c91ff9279c15590b072b2ea095d49e33891d\",\"nonce\":254035,\"hash\":\"000048e3a030e5f76106928210ea55877b999672b02061c963532b9890d280cc\",\"products\":[{}],\"height\":1}";
	private static final Logger LOGGER = Logger.getLogger(ServerManager.class.getName());
	
	
	public static synchronized void handleMessage(String message, ServerConnection serverConnection,
			ServerManager serverManager) throws JSONException {
		final Set<String> possibleCommands = new HashSet<String>(
				Arrays.asList("ReqBlock", "ReqProduct", "ReqLightBlockchain", "ReqBlockWithMerkleHash",
						"ReqBlockWithTimestamp", "ReqBlockWithNonce", "ReqBlockWithHeight", "ReqBlockWithBits",
						"ReqBlockWithHash", "ReqBlockWithPreviousHash", "ReqBlockWithProducts", "ReqProdWithName",
						"ReqProdWithHash", "ReqProdWithTimestamp", "ReqProdWithValue", "ReqProdWithSeller",
						"ReqProdWithDescription", "ReqPeers", "addProduct", "AddBlock", "AddPeers", "Exit"));
		
		if (message.contains("#")) {
			String[] part = message.split("#");
			
			if (part.length == 2) {
				if (part[0] != null && part[1] != null) {
					String commandMessage = part[0];
					String commandPayload = part[1];
					
					if (possibleCommands.contains(commandMessage)) {
						if (commandMessage.toLowerCase().equals("AddProduct".toLowerCase())) {
							JSONObject product = StringToJSONObject.stringToJSONObject(commandPayload);
							LOGGER.log(Level.INFO, "addProduct request received: " + product.toString());
						
							try {
								Blockchain.addProduct(product);
							} catch (InvalidKeyException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (UnknownHostException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchAlgorithmException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchProviderException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SignatureException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvalidAlgorithmParameterException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (commandMessage.toLowerCase().equals("AddBlock".toLowerCase())) {
							JSONObject block = StringToJSONObject.stringToJSONObject(commandPayload);
							LOGGER.log(Level.INFO, "addBlock request received: " + block.get("hash"));
							
							try {
								Blockchain.addJSONObjBlock(block);
							} catch (DBException e) {
								// TODO Auto-generated catch block
								e.printStackTrace(); 
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							//serverConnection.broadcastMessageToClients(commandMessage);
						}
						if (commandMessage.toLowerCase().equals("AddPeers".toLowerCase())) {
							String[] peers = commandPayload.split(",");

							for (String peer : peers) {
								serverManager.startConnection(peer);
							}
							serverConnection.broadcastMessageToClients(commandMessage);
						}
						if (commandMessage.toLowerCase().equals("ReqBlock".toLowerCase())) {
							serverConnection.broadcastMessageToClient(commandMessage);
						}
						if (commandMessage.toLowerCase().equals("ReqPeers".toLowerCase())) {
							serverConnection.broadcastMessageToClient(serverConnection.getClients());
						}
						if (commandMessage.toLowerCase().equals("ReqProduct".toLowerCase())) {
							serverConnection.broadcastMessageToClient(commandMessage);
						}
						if (commandMessage.toLowerCase().equals("ReqLightBlockchain".toLowerCase())) {
							try {
								serverConnection.broadcastMessageToClient(Blockchain.getJSONArrayLightChain().toString());
							} catch (DBException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (commandMessage.toLowerCase().equals("ReqBlockWithMerkleHash".toLowerCase())) {
							serverConnection.broadcastMessageToClient(commandMessage);
						}
						if (commandMessage.toLowerCase().equals("ReqBlockWithTimestamp".toLowerCase())) {
							serverConnection.broadcastMessageToClient(commandMessage);
						}
						if (commandMessage.toLowerCase().equals("ReqBlockWithNonce".toLowerCase())) {
							serverConnection.broadcastMessageToClient(commandMessage);
						}
						if (commandMessage.toLowerCase().equals("ReqBlockWithHeight".toLowerCase())) {
							serverConnection.broadcastMessageToClient(commandMessage);
						}
						if (commandMessage.toLowerCase().equals("ReqBlockWithBits".toLowerCase())) {
							serverConnection.broadcastMessageToClient(commandMessage);
						}
						if (commandMessage.toLowerCase().equals("ReqBlockWithHash".toLowerCase())) {
							try {
								serverConnection.broadcastMessageToClient(Blockchain.getJSONObjBlockWithHash(commandPayload).toString());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (commandMessage.toLowerCase().equals("ReqBlockWithPreviousHash".toLowerCase())) {
							serverConnection.broadcastMessageToClient(commandMessage);
						}
						if (commandMessage.toLowerCase().equals("ReqBlockWithProducts".toLowerCase())) {
							serverConnection.broadcastMessageToClient(commandMessage);
						}
						if (commandMessage.toLowerCase().equals("ReqBlockWithProducts".toLowerCase())) {
							serverConnection.broadcastMessageToClient(commandMessage);
						}
						if (commandMessage.toLowerCase().equals("ReqProdWithHash".toLowerCase())) {
							serverConnection.broadcastMessageToClient(commandMessage);
						}
						if (commandMessage.toLowerCase().equals("ReqProdWithTimestamp".toLowerCase())) {
							serverConnection.broadcastMessageToClient(commandMessage);
						}
						if (commandMessage.toLowerCase().equals("ReqProdWithValue".toLowerCase())) {
							serverConnection.broadcastMessageToClient(commandMessage);
						}
						if (commandMessage.toLowerCase().equals("ReqProdWithSeller".toLowerCase())) {
							serverConnection.broadcastMessageToClient(commandMessage);
						}
						if (commandMessage.toLowerCase().equals("ReqProdWithDescription".toLowerCase())) {
							serverConnection.broadcastMessageToClient(commandMessage);
						}
					}
				}
			}
		} else {
			if (message.toLowerCase().equals("exit")) {
				try {
					serverConnection.close(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (message.toLowerCase().equals("ping")) {
				serverConnection.broadcastMessageToClient("pong");
			}
			if (message.toLowerCase().equals("clientversion")) {
				serverConnection.broadcastMessageToClient("1.0");
			}
			if (message.toLowerCase().equals("info")) {
				JSONObject info = new JSONObject();
				info.put("clientVersion", "1.1");
				info.put("clientsConnected", serverManager.connections.size());
				info.put("lastBlock", 0);
				info.put("peerId", serverConnection.hashCode());
				serverConnection.broadcastMessageToClient(info.toString());
			}
		}		
	}
}
