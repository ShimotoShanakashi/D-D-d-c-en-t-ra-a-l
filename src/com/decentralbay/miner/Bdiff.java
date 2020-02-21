package com.decentralbay.miner;

import java.math.BigInteger;

public class Bdiff {
	public static double getBdiff(String bits) {
		String bdiff = "00000000FFFF0000000000000000000000000000000000000000000000000000";
		String hexTarget = HexTarget.hexTarget(bits);
		
		BigInteger BigBdiff = new BigInteger(bdiff, 16);
		BigInteger HexTarget = new BigInteger(hexTarget, 16);
		
		double result = BigBdiff.doubleValue() / HexTarget.doubleValue();
		return result;
	}
}
