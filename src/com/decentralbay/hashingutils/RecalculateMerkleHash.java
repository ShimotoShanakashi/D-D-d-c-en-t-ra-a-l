package com.decentralbay.hashingutils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.decentralbay.utils.MerklerootUtil;

public class RecalculateMerkleHash {
	public static String hash(ArrayList<JSONObject> Products) throws JSONException {
		List <String>hexHashes = new ArrayList<String>();
		for (int i = 0; i < Products.size(); i++) {
			hexHashes.add(Products.get(i).getString("productHash"));
		}
		
		MerklerootUtil root = new MerklerootUtil(hexHashes);
		root.merkle_tree();

		return root.getRoot();
	}
}
