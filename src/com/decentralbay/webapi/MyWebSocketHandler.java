package com.decentralbay.webapi;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.iq80.leveldb.DBException;
import org.json.JSONException;




@WebSocket
public class MyWebSocketHandler {
	Session session;
	private final static Logger logger = Logger.getLogger(MyWebSocketHandler.class.getName());
	
	public MyWebSocketHandler() {
	}
	
    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        //System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
        logger.log(Level.WARNING, "Close: statusCode=" + statusCode + ", reason=" + reason);
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        System.out.println("Error: " + t.getMessage());
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
    	// implements
    	//session.setIdleTimeout(-1L);
    	session.setIdleTimeout(20000);
    	this.session = session;
        //System.out.println("Connect: " + session.getRemoteAddress().getAddress());
        logger.log(Level.INFO, "Web API: connect! " + session.getRemoteAddress().getAddress());
        try {
            session.getRemote().sendString("Established connection");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String message) throws IOException, DBException, JSONException {
    	if(message.contains("#")) {
	    	String[] parts = message.split("#");
			//System.out.println(message);
			String part1 = parts[0]; // 123
			String part2 = parts[1];
			/*
			 * - Types of Requests for querying info in the blockchain
			 * 
			 * 		ReqBlock - Req Block at index
			 * 		ReqProduct - Req ProductMatching name
			 * 		ReqLightBlockchain - Req Whole Light Blockchain 
			 * 
			 * 		-Blocks: 
			 * 
			 * 		ReqBlockWithMerkleHash - Req Block matching merkle hash
			 * 		ReqBlockWithTimestamp - Req Block matching timestamp
			 * 		ReqBlockWithNonce - Req Block matching Nonce
			 * 		ReqBlockWithHeight - Req Block matching Height
			 * 		ReqBlockWithBits - Req Block matching Bits
			 * 		ReqBlockWithHash - Req Block matching Hash
			 * 		ReqBlockWithPreviousHash - Req Block matching Previous Hash
			 * 		ReqBlockWithProducts - Req Block products array
			 * 
			 * 		-Products: 
			 * 
			 * 		ReqProdWithName
			 * 		ReqProdWithHash
			 * 		ReqProdWithTimestamp
			 * 		ReqProdWithValue
			 * 		ReqProdWithSeller
			 * 		ReqProdWithDescription
			 * - Types of Request for sending info to the blockchain
			 * 
			 */
			
			if (part1.equals("addBlock") || part1.equals("addProduct") || part1.equals("getAccountBalance") || part1.equals("getProduct") || part1.equals("getBlock") || part1.equals("ReqBlockWithHash") || part1.equals("getBlockchain") || part1.equals("addProduct")) {
				if (part1.equals("addBlock")) {
					String request = Client.RequestToServer("addBlock", part2);
		        	sendStringToClient(request.toString());
		        }
		        if (part1.equals("ReqBlock")) {
		        	// System.out.println("Received ReqBlock request");
		        	// System.out.println("Received from server" + Client.RequestToServer("getBlock", part2).toString());
		        	logger.log(Level.INFO, "Web API: connect! Received ReqBlock request! " + Client.RequestToServer("getBlock", part2).toString());
		        	sendStringToClient(Client.RequestToServer("getBlock", part2).toString());
		        }
		        if (part1.equals("ReqBlockWithHash")) {
		        	//System.out.println("Received ReqBlockWithHash request");
		        	
	    			String response = Client.RequestToServer("ReqBlockWithHash", part2).toString();
	    			logger.log(Level.INFO, "Web API: connect! Received ReqBlockWithHash request! " + part2);
	    			//System.out.println("Received from server: " + response);
	    			
	    			sendStringToClient(response);
		        }
		        if (part1.equals("ReqBlockHeight" )) {
		        	//System.out.println("Received ReqBlockHeight request");
	    			
	    			String response = Client.RequestToServer("ReqBlockHeight", part2).toString();
	    			//System.out.println("Received from server: " + response);
	    			logger.log(Level.INFO, "Web API: connect! Received ReqBlockHeight request! " + part2);
	    			
	    			sendStringToClient(response);
		        }
		        if (part1.equals("addProduct" )) {
		        	//System.out.println("Received addServerProduct");
	    			
	    			Client.RequestToServer("addProduct", part2).toString();
	    			logger.log(Level.INFO, "Web API: connect! Received addServerProduct request! " + part2);
	    			sendStringToClient("Product sent to server from " + session.getRemoteAddress());
		        }
			} else {
				// ERROR 400: UNKNOWN COMMAND
				// IMPLEMENTATION OF ERROR CONTROL AND ERROR MESSAGING
				sendStringToClient("400");
			}
    	} else {
    		if (message.equals("ReqLightBlockchain")) {
    			//System.out.println("Received ReqLightBlockchain request");
    			
    			
    			String response = Client.RequestToServer("ReqLightBlockchain", "asd").toString();
    			logger.log(Level.INFO, "Web API: connect! Received ReqLightBlockchain request!");
    			sendStringToClient(response);
            }
    	}
    	
        
        
        if (message.isEmpty()) {
        }
    }
        
    
    public void sendStringToClient(String string) throws IOException {
    	this.session.getRemote().sendString(string);
    }
}

