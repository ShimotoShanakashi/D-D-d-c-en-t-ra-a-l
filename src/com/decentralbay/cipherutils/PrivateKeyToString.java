package com.decentralbay.cipherutils;

import java.security.PrivateKey;
import java.util.Base64;




public class PrivateKeyToString {
	public static String privateKeyToString(PrivateKey privKey) {
		String priv1 = Base64.getEncoder().encodeToString(privKey.getEncoded());
		return priv1;
	}
}
