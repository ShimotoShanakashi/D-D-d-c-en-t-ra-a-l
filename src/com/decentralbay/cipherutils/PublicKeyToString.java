package com.decentralbay.cipherutils;


import java.security.*;
import java.util.Base64;




public class PublicKeyToString {
	public static String publicKeyToString(PublicKey pbKey) {
		String pub1 = Base64.getEncoder().encodeToString(pbKey.getEncoded());
		return pub1;
	}
}
