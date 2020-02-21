package com.decentralbay.blockchecks;

import java.math.BigInteger;


import org.json.JSONException;
import org.json.JSONObject;

import com.decentralbay.utils.StringUtil;

public class BlockNonceVerifier {
	public static boolean check(JSONObject block) throws JSONException {
		long timeStamp = block.getLong("timeStamp");
		String previousHash = block.getString("previousHash");
		String serverWinner = block.getString("serverWinner");
		String bits = block.getString("bits");
		String merkleRoot = block.getString("merkleRoot");
		int nonce = block.getInt("nonce");
		String hash = block.getString("hash");		
		
		
		//BigInteger GlobalTarget = new BigInteger(hexTarget(bits) + "0000000000000000000000000000000000000000000000000000000", 16);
		BigInteger GlobalTarget = new BigInteger(HexTarget.hexTarget(bits) + "0000000000000000000000000000000000000000000000000000000", 16);
//		System.out.println("Network target is at: " + GlobalTarget);
		BigInteger DEC3bigInt = new BigInteger(StringUtil.applySha256(previousHash + Long.toString(timeStamp) + serverWinner + nonce + merkleRoot), 16);
		DEC3bigInt = new BigInteger(StringUtil.applySha256(previousHash + Long.toString(timeStamp) + serverWinner + nonce + merkleRoot), 16);
		
//		System.out.println(DEC3bigInt + " " + GlobalTarget);
		if(DEC3bigInt.compareTo(GlobalTarget) == -1) {			
			return true;
	    } else {
	    	return false;
	    }
	}
	
	
	public static boolean check(String blockx) throws JSONException {
		JSONObject block = new JSONObject(blockx);
		long timeStamp = block.getLong("timeStamp");
		String previousHash = block.getString("previousHash");
		String serverWinner = block.getString("serverWinner");
		String bits = block.getString("bits");
		String merkleRoot = block.getString("merkleRoot");
		int nonce = block.getInt("nonce");
		String hash = block.getString("hash");		
		
		
		//BigInteger GlobalTarget = new BigInteger(hexTarget(bits) + "0000000000000000000000000000000000000000000000000000000", 16);
		BigInteger GlobalTarget = new BigInteger(HexTarget.hexTarget(bits) + "0000000000000000000000000000000000000000000000000000000", 16);
//		System.out.println("Network target is at: " + GlobalTarget);
		BigInteger DEC3bigInt = new BigInteger(StringUtil.applySha256(previousHash + Long.toString(timeStamp) + serverWinner + nonce + merkleRoot), 16);
		DEC3bigInt = new BigInteger(StringUtil.applySha256(previousHash + Long.toString(timeStamp) + serverWinner + nonce + merkleRoot), 16);
		
//		System.out.println(DEC3bigInt + " " + GlobalTarget);
		if(DEC3bigInt.compareTo(GlobalTarget) == -1) {			
			return true;
	    } else {
	    	return false;
	    }		
	}
}
