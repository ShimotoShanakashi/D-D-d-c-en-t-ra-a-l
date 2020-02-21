package com.decentralbay.core;


import java.time.Instant;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import com.decentralbay.hashingutils.RecalculateMerkleHash;
import com.decentralbay.utils.ApplySha256;

public class Block {
	private String hash; 
	private String previousHash;
	private String serverWinner;
	//looking forward implementation with mining libraries
	private String bits = "034b11a";
	private long timeStamp;
	private int nonce = 0;
	private String merkleRoot;
	private int height;
	
	private ArrayList<JSONObject> products = new ArrayList<JSONObject>();
	private final static Logger logger = Logger.getLogger(Block.class.getName());
	
	
	/** 
	 * Block constructor, will take first server to broadcast the block, hence the winner of the reward 
	 * **/
	public Block(int height, String serverWinner, String previousHash, ArrayList<JSONObject> products) throws JSONException {
		this.height = height;
		this.serverWinner = serverWinner;
		this.previousHash = previousHash;
		this.products.addAll(products);
		this.hash = calculateHash();
		this.timeStamp = Instant.now().toEpochMilli();
	}
	
	
	/**
	 * Calculates Block hash by hashing (previousHash + Long.toString(timeStamp) + serverWinner + nonce + this.merkleRoot)
	 * @return String
	 * @throws JSONException
	 */
	public String calculateHash() throws JSONException {
		String calculatedhash = ApplySha256.applySha256(previousHash + Long.toString(timeStamp) + serverWinner + nonce + this.merkleRoot);
		return calculatedhash;
	}

	
	/**
	 * Sets the merkle hash without duplicated products
	 * @throws JSONException
	 */
	public void setMerkleRoot() throws JSONException {		
		this.merkleRoot = RecalculateMerkleHash.hash(products);
	}


	// Getters and setters
	public String getHash() {
		return hash;
	}


	public void setHash(String hash) {
		this.hash = hash;
	}


	public String getPreviousHash() {
		return previousHash;
	}


	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}


	public String getServerWinner() {
		return serverWinner;
	}


	public void setServerWinner(String serverWinner) {
		this.serverWinner = serverWinner;
	}


	public String getBits() {
		return bits;
	}


	public void setBits(String bits) {
		this.bits = bits;
	}


	public long getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}


	public int getNonce() {
		return nonce;
	}


	public void setNonce(int nonce) {
		this.nonce = nonce;
	}


	public String getMerkleRoot() {
		return merkleRoot;
	}


	public void setMerkleRoot(String merkleRoot) {
		this.merkleRoot = merkleRoot;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public ArrayList<JSONObject> getProducts() {
		return products;
	}


	public void setProducts(ArrayList<JSONObject> products) {
		this.products = products;
	}
}
