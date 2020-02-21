package com.decentralbay.blockchecks;

import org.json.JSONException;
import org.json.JSONObject;

import com.decentralbay.core.Config;

public class ProductLengthVerifier {
	public static boolean check(JSONObject block) throws JSONException {
		int productsLenght = block.getJSONArray("products").length();
		
		if(productsLenght < Config.maxblocks || productsLenght > 200) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean check(String blockx) throws JSONException {
		JSONObject block = new JSONObject(blockx);
		int productsLenght = block.getJSONArray("products").length();
		
		if(productsLenght < Config.maxblocks || productsLenght > 200) {
			return false;
		} else {
			return true;
		}
	}
}
