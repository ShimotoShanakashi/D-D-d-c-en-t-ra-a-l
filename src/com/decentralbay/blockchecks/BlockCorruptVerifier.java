package com.decentralbay.blockchecks;

import org.json.JSONException;
import org.json.JSONObject;

/*	Block:
 *
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

public class BlockCorruptVerifier {
	public static boolean check(JSONObject block) throws JSONException {
		int timeStampLenght = 0;
		int previousHashLenght = 0;
		int serverWinnerLenght = 0;
		int bitsLenght = 0;
		int merkleRootLenght = 0;
		int nonceLenght = 0;
		int hashLenght = 0;
		int heightLenght = 0;
		int algorithmLength = 0;
		
		if(BlockFormatVerifier.check(block) == true && ProductLengthVerifier.check(block) == true) {
			timeStampLenght = String.valueOf(block.getLong("timeStamp")).length();
			previousHashLenght = block.getString("previousHash").length();
			serverWinnerLenght = block.getString("serverWinner").length();
			bitsLenght = block.getString("bits").length();
			merkleRootLenght = block.getString("merkleRoot").length();
			nonceLenght = String.valueOf(block.getInt("nonce")).length();
			hashLenght = block.getString("hash").length();
			heightLenght = String.valueOf(block.getInt("height")).length();
		}
		if(timeStampLenght > 0 && previousHashLenght > 0 && serverWinnerLenght > 0 && bitsLenght > 0 && merkleRootLenght > 0 && nonceLenght > 0 && hashLenght > 0 && 
				heightLenght > 0) {

			return true;		
		} else {
			return false;
		}
	}
	
	public static boolean check(String blockx) throws JSONException {
		JSONObject block = new JSONObject(blockx);
		int timeStampLenght = 0;
		int previousHashLenght = 0;
		int serverWinnerLenght = 0;
		int bitsLenght = 0;
		int merkleRootLenght = 0;
		int nonceLenght = 0;
		int hashLenght = 0;
		int heightLenght = 0;
		int algorithmLength = 0;
		
		if(BlockFormatVerifier.check(block) == true && ProductLengthVerifier.check(block) == true) {
			timeStampLenght = String.valueOf(block.getLong("timeStamp")).length();
			previousHashLenght = block.getString("previousHash").length();
			serverWinnerLenght = block.getString("serverWinner").length();
			bitsLenght = block.getString("bits").length();
			merkleRootLenght = block.getString("merkleRoot").length();
			nonceLenght = String.valueOf(block.getInt("nonce")).length();
			hashLenght = block.getString("hash").length();
			heightLenght = String.valueOf(block.getInt("height")).length();
		}
		if(timeStampLenght > 0 && previousHashLenght > 0 && serverWinnerLenght > 0 && bitsLenght > 0 && merkleRootLenght > 0 && nonceLenght > 0 && hashLenght > 0 && 
				heightLenght > 0) {

			return true;		
		} else {
			return false;
		}
	}
}
