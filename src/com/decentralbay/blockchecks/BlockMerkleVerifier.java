package com.decentralbay.blockchecks;

import java.util.ArrayList;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.decentralbay.utils.MerklerootUtil;


public class BlockMerkleVerifier {
	public static boolean check(JSONObject block) throws JSONException {
		String merkleRoot = block.getString("merkleRoot");
		JSONArray Products = block.getJSONArray("products");
		List<String> hexHashes = new ArrayList<String>();
		for (int i = 0; i < Products.length(); i++) {
			// System.out.println("product " + i + " hash: " + Products.getJSONObject(i).getString("productHash"));
			hexHashes.add(Products.getJSONObject(i).getString("productHash"));
		}
		
		MerklerootUtil root = new MerklerootUtil(hexHashes);
		root.merkle_tree();
		// System.out.println(root.getRoot() + " " + merkleRoot);
		if(root.getRoot().equals(merkleRoot)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public static boolean check(String blockx) throws JSONException {
		JSONObject block = new JSONObject(blockx);
		String merkleRoot = block.getString("merkleRoot");
		JSONArray Products = block.getJSONArray("products");
		List<String> hexHashes = new ArrayList<String>();
		for (int i = 0; i < Products.length(); i++) {
			// System.out.println("product " + i + " hash: " + Products.getJSONObject(i).getString("productHash"));
			hexHashes.add(Products.getJSONObject(i).getString("productHash"));
		}
		
		MerklerootUtil root = new MerklerootUtil(hexHashes);
		root.merkle_tree();
		// System.out.println(root.getRoot() + " " + merkleRoot);
		if(root.getRoot().equals(merkleRoot)) {
			return true;
		} else {
			return false;
		}
	}
}
