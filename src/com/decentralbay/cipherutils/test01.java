package com.decentralbay.cipherutils;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.util.encoders.Hex;

import com.decentralbay.wallet.Wallet;

public class test01 {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeySpecException {
		Wallet wallet = new Wallet();
		
		String pbKey = Base64.getEncoder().encodeToString(wallet.publicKey.getEncoded());
		String pvKey = Base64.getEncoder().encodeToString(wallet.privateKey.getEncoded());
		
		System.out.println(wallet.publicKey);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		PublicKeyStringToObject.publicKeyStringToObject(pbKey);
		System.out.println(PublicKeyStringToObject.publicKeyStringToObject(pbKey));
		PrivateKeyStringToObject.privateKeyStringToObject(pvKey);
		
		
		
	}
	
	
	public static PrivateKey getPrivateKeyFromECBigIntAndCurve(BigInteger s) {

	    ECParameterSpec ecParameterSpec = ECNamedCurveTable.getParameterSpec("secp256k1");
	    ECPrivateKeySpec privateKeySpec = new ECPrivateKeySpec(s, ecParameterSpec);
	    
	    try {
	        KeyFactory keyFactory = KeyFactory.getInstance("EC");
	        return keyFactory.generatePrivate(privateKeySpec);
	    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	
	public static void saveKey(PrivateKey key) throws IOException {
	    byte[] encoded = key.getEncoded();
	    byte[] hex = Hex.encode(encoded);
	    String data = String.valueOf(hex);
	}
}
