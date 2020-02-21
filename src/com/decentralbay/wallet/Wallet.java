package com.decentralbay.wallet;

import java.security.*;
import java.security.spec.ECGenParameterSpec;




public class Wallet {
	public KeyPair keypair;
	public PublicKey publicKey;
	public PrivateKey privateKey;

	
	public Wallet() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		generateKeys();
	}
	
	private void generateKeys() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
		KeyPairGenerator g = KeyPairGenerator.getInstance("EC");
		g.initialize(ecSpec, new SecureRandom());
		keypair = g.generateKeyPair();
		publicKey = keypair.getPublic();
		System.out.println("PUBLICKEY WALLET: " + publicKey);
		privateKey = keypair.getPrivate(); 
	}
}
