package com.decentralbay.utils;

import java.security.MessageDigest;

import org.json.JSONException;
import org.json.JSONObject;

public class StringUtil {
	public static JSONObject ConvertToJsonObject(String x) throws JSONException {
		JSONObject jsonObject = new JSONObject(x);
		return jsonObject;
	}
	
	public static String JSONObjectToString(JSONObject obj) {
		return obj.toString();
	}
	
	public static String applySha256(String input) {		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
			//Applies sha256 to our input, 
			byte[] hash = digest.digest(input.getBytes("UTF-8"));	        
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
