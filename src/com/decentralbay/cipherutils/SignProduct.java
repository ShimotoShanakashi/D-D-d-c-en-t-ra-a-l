package com.decentralbay.cipherutils;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONException;
import org.json.JSONObject;

import com.decentralbay.wallet.Wallet;




public class SignProduct {
//	public static JSONObject signProduct(PublicKey sellerAddress, PrivateKey privateKey, String productHash, float productValue, String timestamp, String description, String tags, String productData, String imgLink) throws JSONException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException, UnsupportedEncodingException {
//		Security.addProvider(new BouncyCastleProvider());
//		Signature ecdsaSign = Signature.getInstance("SHA256withECDSA", "BC");
//		ecdsaSign.initSign(privateKey);
//		ecdsaSign.update(productHash.getBytes("UTF-8"));
//		byte[] signature = ecdsaSign.sign();
//		// byte[] -> String -> base64
//		byte[] addr = sellerAddress.getEncoded();
//		for (int i = 0; i < addr.length; i++) {
//			System.out.print(", " + addr[i]);
//		}
//		System.out.println(" | ");
//		String pub1 = Base64.getEncoder().encodeToString(sellerAddress.getEncoded());
//		String sig = Base64.getEncoder().encodeToString(signature);
//		
//		//System.out.println("From Address: " + fromAddress);
//		//System.out.println("To Address: " + toAddress);
//		//System.out.println("Signature: " + sig);
//		
//		JSONObject obj = new JSONObject();
//		obj.put("description", description);
//		obj.put("sellerAddress", pub1);
//		obj.put("name", productData);
//		obj.put("tags", tags);
//		obj.put("productValue", String.valueOf(productValue));
//		obj.put("timestamp", timestamp);
//		obj.put("signature", sig);
//		obj.put("imgLink", imgLink);
//		obj.put("productHash", productHash);
//		obj.put("algorithm", "SHA256withECDSA");
//		return obj;
//	}
	
//	this.sellerAddress, this.serverAddress, this.sellerKey, this.serverKey, this.name, this.productValue, this.imgLink, 
//	this.tags, this.timestamp, this.description, this.serverIp
	public static JSONObject signProduct(PublicKey sellerAddress, PublicKey serverAddress, PrivateKey sellerKey, PrivateKey serverKey,
			String name, float productValue, String imgLink, String tags, long timestamp, String description, String serverIp, String productHash) throws JSONException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException, UnsupportedEncodingException {
		
		Security.addProvider(new BouncyCastleProvider());
		Signature ecdsaSignSeller = Signature.getInstance("SHA256withECDSA", "BC");
		
		ecdsaSignSeller.initSign(sellerKey);
		ecdsaSignSeller.update(productHash.getBytes("UTF-8"));
		byte[] signature = ecdsaSignSeller.sign();

		
		String sellerAddressx = Base64.getEncoder().encodeToString(sellerAddress.getEncoded());
		String signaturex = Base64.getEncoder().encodeToString(signature);
		
		
		Security.addProvider(new BouncyCastleProvider());
		Signature ecdsaServerSeller = Signature.getInstance("SHA256withECDSA", "BC");
		
		ecdsaServerSeller.initSign(serverKey);
		ecdsaServerSeller.update(productHash.getBytes("UTF-8"));
		byte[] serverSignature = ecdsaServerSeller.sign();
		
		String serverAddressx = Base64.getEncoder().encodeToString(sellerAddress.getEncoded());
		String serverSignaturex = Base64.getEncoder().encodeToString(signature);
		//System.out.println("From Address: " + fromAddress);
		//System.out.println("To Address: " + toAddress);
		//System.out.println("Signature: " + sig);
		
		JSONObject obj = new JSONObject();
		obj.put("name", name);
		obj.put("productValue", String.valueOf(productValue));
		obj.put("imgLink", imgLink);
		obj.put("signature", signaturex);
		obj.put("productHash", productHash);
		obj.put("tags", tags);
		obj.put("timestamp", timestamp);
		obj.put("sellerAddress", sellerAddressx);
		obj.put("description", description);
		obj.put("serverIp", serverIp);
		obj.put("serverAddress", serverAddressx);
		obj.put("serverSignature", serverSignaturex);		
		obj.put("algorithm", "SHA256withECDSA");
		return obj;
	}
	
	
	public static JSONObject signServerProduct(JSONObject Product) throws JSONException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
		Security.addProvider(new BouncyCastleProvider());
		String name = ""; 
		String productValue = "";
		String imgLink = "";
		String signature = "";
		String productHash = "";
		String tags = "";
		String timestamp = "";
		String sellerAddress = "";
		String description = "";
		String serverIp = "";
		String serverAddress = "";
		String serverSignatureString = "";
		String algorithm = "";
		Wallet wallet = new Wallet();
		
		try {
			name = Product.getString("name");
			productValue = Product.getString("productValue");
			imgLink = Product.getString("imgLink");
			signature = Product.getString("signature");
			productHash = Product.getString("productHash");
			tags = Product.getString("tags");
			timestamp = Product.getString("timestamp");
			sellerAddress = Product.getString("sellerAddress");
			description = Product.getString("description");
			serverIp = Product.getString("serverIp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Signature ecdsaServerSeller = Signature.getInstance("SHA256withECDSA", "BC");
		
		ecdsaServerSeller.initSign(wallet.privateKey);
		ecdsaServerSeller.update(productHash.getBytes("UTF-8"));
		byte[] serverSignature = ecdsaServerSeller.sign();
		
		String serverAddressx = Base64.getEncoder().encodeToString(wallet.publicKey.getEncoded());
		serverSignatureString = Base64.getEncoder().encodeToString(serverSignature);

		
		JSONObject obj = new JSONObject();
		obj.put("name", name);
		obj.put("productValue", String.valueOf(productValue));
		obj.put("imgLink", imgLink);
		obj.put("signature", serverSignatureString);
		obj.put("productHash", productHash);
		obj.put("tags", tags);
		obj.put("timestamp", timestamp);
		obj.put("sellerAddress", sellerAddress);
		obj.put("description", description);
		obj.put("serverIp", serverIp);
		obj.put("serverAddress", serverAddressx);
		obj.put("serverSignature", serverSignatureString);		
		obj.put("algorithm", "SHA256withECDSA");
		return obj;
	}
}
