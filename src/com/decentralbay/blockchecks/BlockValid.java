package com.decentralbay.blockchecks;

import org.json.JSONException;
import org.json.JSONObject;

public class BlockValid {
	public static boolean isBlockValid(JSONObject block) throws JSONException {
		if(BlockCorruptVerifier.check(block) 
				&& BlockFormatVerifier.check(block) 
				&& BlockHashVerifier.check(block) 
				&& BlockTimestampVerifier.check(block) 
				&& ProductLengthVerifier.check(block)) {
			return true;
		} 
		return false;
	}
	
	
	public static boolean isBlockValid(String blockx) throws JSONException {
		JSONObject block = new JSONObject(blockx);
		if(BlockCorruptVerifier.check(block) 
				&& BlockFormatVerifier.check(block) 
				&& BlockHashVerifier.check(block) 
				&& BlockTimestampVerifier.check(block) 
				&& ProductLengthVerifier.check(block)) {
			return true;
		} 
		return false;
	}
}
