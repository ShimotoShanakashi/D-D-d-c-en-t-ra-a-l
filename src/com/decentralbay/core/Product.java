package com.decentralbay.core;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import com.decentralbay.cipherutils.PrivateKeyStringToObject;
import com.decentralbay.cipherutils.PublicKeyStringToObject;
import com.decentralbay.cipherutils.SignProduct;
import com.decentralbay.utils.StringUtil;

public class Product {
	private String name;
	private float productValue;
	private String productStatus;
	private String imgLink;
	private String tags;
	private long timestamp;
	private String description;
	private PublicKey sellerAddress;
	private PrivateKey sellerKey;
	private PublicKey serverAddress;
	private PrivateKey serverKey;
	private String serverIp;
	private String productHash;
	 
	
	
	public Product(String name, String productValue, String productStatus, String imgLink, String tags, String description, String sellerAddress, String sellerKey, 
			String serverAddress, String serverKey, String serverIp) throws NoSuchAlgorithmException, InvalidKeySpecException {
		this.name = name;
		this.productValue = Float.valueOf(productValue);
		this.productStatus = productStatus;
		this.imgLink = imgLink;
		this.tags = tags;
		this.timestamp = Instant.now().toEpochMilli();
		this.description = description;
		this.sellerAddress = PublicKeyStringToObject.publicKeyStringToObject(sellerAddress);
		this.sellerKey = PrivateKeyStringToObject.privateKeyStringToObject(sellerKey);
		this.serverAddress = PublicKeyStringToObject.publicKeyStringToObject(serverAddress);
		this.serverKey = PrivateKeyStringToObject.privateKeyStringToObject(serverKey);
		this.serverIp = serverIp;
		this.productHash = calculateHash(); 
	}
	
	
	public Product(String name, String productValue, String productStatus, String imgLink, String tags, String description, PublicKey sellerAddress, PrivateKey sellerKey, 
			PublicKey serverAddress, PrivateKey serverKey, String serverIp) throws NoSuchAlgorithmException, InvalidKeySpecException {
		this.name = name;
		this.productValue = Float.valueOf(productValue);
		this.productStatus = productStatus;
		this.imgLink = imgLink;
		this.tags = tags;
		this.timestamp = Instant.now().toEpochMilli();
		this.description = description;
		this.sellerAddress = sellerAddress;
		this.sellerKey = sellerKey;
		this.serverAddress = serverAddress;
		this.serverKey = serverKey;
		this.serverIp = serverIp;
		this.productHash = calculateHash(); 
	}
	
	
	public String calculateHash() {
		return StringUtil.applySha256(this.name + String.valueOf(this.productValue) + this.imgLink + this.tags + Long.toString(this.timestamp) + Base64.getEncoder().encodeToString(this.sellerAddress.getEncoded()) + this.description + this.productStatus);
	}
	
	
	public JSONObject signProduct() throws InvalidKeyException, NoSuchAlgorithmException, SignatureException, UnsupportedEncodingException, NoSuchProviderException, JSONException {
		JSONObject obj = SignProduct.signProduct(this.sellerAddress, this.serverAddress, this.sellerKey, this.serverKey, this.name, this.productValue, this.imgLink, 
				this.tags, this.timestamp, this.description, this.serverIp, this.productHash);
		return obj;
	}
}
