package com.decentralbay.blockchecks;

import java.time.Instant;

import org.json.JSONException;
import org.json.JSONObject;

public class BlockTimestampVerifier {
	public static boolean check(JSONObject block) throws JSONException {
		// TODO Auto-generated method stub
		long blockTimestamp;
		long futureTimestamp = Instant.now().toEpochMilli() + 3600000; 
		
		try {
			blockTimestamp = block.getLong("timeStamp");
		} catch (Exception e) {
			return false;
		}
		if(blockTimestamp < futureTimestamp) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public static boolean check(String blockx) throws JSONException {
		JSONObject block = new JSONObject(blockx);
		long blockTimestamp;
		long futureTimestamp = Instant.now().toEpochMilli() + 3600000; 
		
		try {
			blockTimestamp = block.getLong("timeStamp");
		} catch (Exception e) {
			return false;
		}
		
		if(blockTimestamp < futureTimestamp) {
			return true;
		} else {
			return false;
		}
	}
}
