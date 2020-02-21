package com.decentralbay.cipherutils;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;




public class VerifyProduct {
	
	public static boolean verifyProduct(JSONObject obj, PublicKey pbKey) throws NoSuchAlgorithmException, JSONException, InvalidKeySpecException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
		Signature ecdsaVerify = Signature.getInstance(obj.getString("algorithm"));
		// KeyFactory kf = KeyFactory.getInstance("EC");
		// EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(obj.getString("publicKey")));
		
		// KeyFactory keyFactory = KeyFactory.getInstance("EC");
		// PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		
		ecdsaVerify.initVerify(pbKey);
		ecdsaVerify.update(obj.getString("transactionHash").getBytes("UTF-8"));
		boolean result = ecdsaVerify.verify(Base64.getDecoder().decode(obj.getString("signature")));


		return result;
	}
}
