package com.decentralbay.blockchecks;

import java.util.ArrayList;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.decentralbay.utils.ApplySha256;
import com.decentralbay.utils.MerklerootUtil;
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

public class BlockHashVerifier {
	public static boolean check(JSONObject block) throws JSONException {
		JSONArray products;
		String blockHash = "";
		String previousHash = "";
		String serverWinner = "";
		String nonce = "";
		
		long timeStamp = 0; 
		try {
			products = block.getJSONArray("products");
			blockHash = block.getString("hash");
			previousHash = block.getString("previousHash");
			serverWinner = block.getString("serverWinner");
			nonce = String.valueOf(block.getInt("nonce"));
			timeStamp = block.getLong("timeStamp");
		} catch (Exception e) {
			return false;
		}
		
		
		List <String> hexHashes = new ArrayList<String>();
		for (int i = 0; i < products.length(); i++) {
			try {
//				hexHashes.add(products.get(i));
				hexHashes.add(String.valueOf(products.getJSONObject(i).get("productHash")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		MerklerootUtil root = new MerklerootUtil(hexHashes);
		root.merkle_tree();
		
		return ApplySha256.applySha256(previousHash + Long.toString(timeStamp) + serverWinner + nonce + root.getRoot()).equals(blockHash);
	}
	
	
	public static boolean check(String blockx) throws JSONException {
		JSONObject block = new JSONObject(blockx);
		JSONArray products;
		String blockHash = "";
		String previousHash = "";
		String serverWinner = "";
		String nonce = "";
		
		long timeStamp = 0; 
		try {
			products = block.getJSONArray("products");
			blockHash = block.getString("hash");
			previousHash = block.getString("previousHash");
			serverWinner = block.getString("serverWinner");
			nonce = String.valueOf(block.getInt("nonce"));
			timeStamp = block.getLong("timeStamp");
		} catch (Exception e) {
			return false;
		}
		List <String> hexHashes = new ArrayList<String>();
		for (int i = 0; i < products.length(); i++) {
			try {
//				hexHashes.add(products.get(i));
				hexHashes.add(String.valueOf(products.getJSONObject(i).get("productHash")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		MerklerootUtil root = new MerklerootUtil(hexHashes);
		root.merkle_tree();
		
		return ApplySha256.applySha256(previousHash + Long.toString(timeStamp) + serverWinner + nonce + root.getRoot()).equals(blockHash);
	}
}
