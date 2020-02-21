package com.decentralbay.cipherutils;


import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;




public class PrivateKeyStringToObject {
	public static PrivateKey privateKeyStringToObject (String privKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(privKey));
		
		KeyFactory keyFactory = KeyFactory.getInstance("EC");
		PrivateKey privateKey = keyFactory.generatePrivate(publicKeySpec);
		return privateKey;
	}
}
