package com.decentralbay.miner;


import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import com.decentralbay.utils.StringUtil;




public class CalculateNonce {
	private final static Logger logger = Logger.getLogger(CalculateNonce.class.getName());
	
	public static JSONObject calculateNonce(JSONObject block) throws JSONException {
		long timeStamp = block.getLong("timeStamp");
		String previousHash = block.getString("previousHash");
		String serverWinner = block.getString("serverWinner");
		String bits = block.getString("bits");
		String merkleRoot = block.getString("merkleRoot");
		int nonce = block.getInt("nonce");
		String hash = block.getString("hash");		
		
		//BigInteger GlobalTarget = new BigInteger(hexTarget(bits) + "0000000000000000000000000000000000000000000000000000000", 16);
		BigInteger GlobalTarget = new BigInteger(HexTarget.hexTarget(bits) + "0000000000000000000000000000000000000000000000000000000", 16);
		BigInteger DEC3bigInt = new BigInteger(StringUtil.applySha256(previousHash + Long.toString(timeStamp) + serverWinner + nonce + merkleRoot), 16);
		
		

		
		int noncex = 0;
		String hashx = "";
		
		while(DEC3bigInt.compareTo(GlobalTarget) != -1) {			
			DEC3bigInt = new BigInteger(StringUtil.applySha256(previousHash + Long.toString(timeStamp) + serverWinner + nonce + merkleRoot), 16);
	    	noncex = nonce;
	    	hashx = StringUtil.applySha256(previousHash + Long.toString(timeStamp) + serverWinner + nonce + merkleRoot);
	    	nonce++;
	    }
		
		block.put("nonce", noncex);
		block.put("hash", hashx);
		
		logger.log(Level.INFO, "\n\n##################################################################################################");
		logger.log(Level.INFO, "Succesfully mined block! " + block.getString("hash"));
		logger.log(Level.INFO, "Nonce of the block: " + block.getInt("nonce"));
		logger.log(Level.INFO, "Global target at: " + GlobalTarget);
		logger.log(Level.INFO, "Mined block with size: " + DEC3bigInt);
		logger.log(Level.INFO, "##################################################################################################\n\n");
		logger.log(Level.INFO, "Valid bl0ck!");

		
		return block;
	}
}
