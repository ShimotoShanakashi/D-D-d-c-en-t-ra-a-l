package com.decentralbay.blockchecks;

import org.json.JSONException;
import org.json.JSONObject;

public class BlockFormatVerifier {
	public static boolean check(JSONObject block) {
		boolean elements = false;
		boolean hasTimestamp = false;
		boolean hasPreviousHash = false;
		boolean hasServerWinner = false;
		boolean hasBits = false;
		boolean hasMerkleRoot = false;
		boolean hasNonce = false;
		boolean hasHash = false;
		boolean hasHeight = false;
		boolean hasProducts = false;
		boolean hasAlgorithm = false;
		
		boolean requiredElements = false;
	/*
		timeStamp
		previousHash
		serverWinner
		bits
		merkleRoot
		nonce
		hash
		height
		products
		algorithm	 
	*/
		// should be 9
		int blockElements = block.length();
		if (blockElements == 9) {
//			System.out.println("Contains valid number of elements");
			if(block.has("timeStamp")) {
				hasTimestamp = true;
			}
			
			if(block.has("previousHash")) {
				hasPreviousHash= true;
			}
			
			if(block.has("serverWinner")) {
				hasServerWinner = true;
			}
			
			if(block.has("bits")) {
				hasBits = true;
			}
			
			if(block.has("merkleRoot")) {
				hasMerkleRoot = true;
			}
			
			if(block.has("nonce")) {
				hasNonce= true;
			}
			
			if(block.has("hash")) {
				hasHash = true;
			}
			
			if(block.has("products")) {
				hasProducts = true;
			}
			
			if(block.has("algorithm")) {
				hasAlgorithm = true;
			}
			
			if(hasTimestamp == true && hasTimestamp == true && hasTimestamp == true 
					&& hasTimestamp == true && hasTimestamp == true && hasTimestamp == true
					&& hasTimestamp == true	&& hasTimestamp == true && hasTimestamp == true) {
//				System.out.println("Contains required elements");
				return true;
			} else {
//				System.out.println("Doesn't contain required elements");
				return false;
			}
		} else {
//			System.out.println("Contains invalid number of elements");
			return false;
		}
	}
	
	
	public static boolean check(String blockx) throws JSONException {
		JSONObject block = new JSONObject(blockx);
		boolean elements = false;
		boolean hasTimestamp = false;
		boolean hasPreviousHash = false;
		boolean hasServerWinner = false;
		boolean hasBits = false;
		boolean hasMerkleRoot = false;
		boolean hasNonce = false;
		boolean hasHash = false;
		boolean hasHeight = false;
		boolean hasProducts = false;
		boolean hasAlgorithm = false;
		
		boolean requiredElements = false;
	/*
		timeStamp
		previousHash
		serverWinner
		bits
		merkleRoot
		nonce
		hash
		height
		products
		algorithm	 
	*/
		// should be 9
		int blockElements = block.length();
		if (blockElements == 9) {
//			System.out.println("Contains valid number of elements");
			if(block.has("timeStamp")) {
				hasTimestamp = true;
			}
			
			if(block.has("previousHash")) {
				hasPreviousHash= true;
			}
			
			if(block.has("serverWinner")) {
				hasServerWinner = true;
			}
			
			if(block.has("bits")) {
				hasBits = true;
			}
			
			if(block.has("merkleRoot")) {
				hasMerkleRoot = true;
			}
			
			if(block.has("nonce")) {
				hasNonce= true;
			}
			
			if(block.has("hash")) {
				hasHash = true;
			}
			
			if(block.has("products")) {
				hasProducts = true;
			}
			
			if(block.has("algorithm")) {
				hasAlgorithm = true;
			}
				
			if(hasTimestamp == true && hasTimestamp == true && hasTimestamp == true 
					&& hasTimestamp == true && hasTimestamp == true && hasTimestamp == true
					&& hasTimestamp == true	&& hasTimestamp == true && hasTimestamp == true) {
//				System.out.println("Contains required elements");
				return true;
			} else {
//				System.out.println("Doesn't contain required elements");
				return false;
			}
		} else {
//			System.out.println("Contains invalid number of elements");
			return false;
		}
		
	}
	
	
//	public static void check(Block block) {
//		
//	}
}
