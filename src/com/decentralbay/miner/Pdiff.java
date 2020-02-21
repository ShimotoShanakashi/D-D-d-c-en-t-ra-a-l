package com.decentralbay.miner;

import java.math.BigInteger;

public class Pdiff {
	public static double getPdiff(String bits) {
		String pdiff = "00000000FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
		String hexTarget = HexTarget.hexTarget(bits);
		
		BigInteger BigPdiff = new BigInteger(pdiff, 16);
		BigInteger HexTarget = new BigInteger(hexTarget, 16);
		
		double result = BigPdiff.doubleValue() / HexTarget.doubleValue();
		return result;
	}
}
