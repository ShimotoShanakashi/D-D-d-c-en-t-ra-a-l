package com.decentralbay.cipherutils;

import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;




public class PublicKeyStringToObject {
	public static PublicKey publicKeyStringToObject (String pbKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
//		Signature ecdsaVerify = Signature.getInstance(obj.getString("algorithm"));
//		KeyFactory kf = KeyFactory.getInstance("EC");
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(pbKey));
		
		KeyFactory keyFactory = KeyFactory.getInstance("EC");
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		return publicKey;
	}
}
