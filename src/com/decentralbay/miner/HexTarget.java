package com.decentralbay.miner;


import java.math.BigInteger;




public class HexTarget {	
	public static String hexTarget(String bits) {
		// x * (2 ** (8 * (y - 3)))
		BigInteger x = new BigInteger(bits.substring(2, bits.length()), 16);
		BigInteger y = new BigInteger(bits.substring(0, 2), 16);
		BigInteger eight = BigInteger.valueOf(8);

		
		long partOne = x.longValue();
		int partTwo = eight.intValue() * (y.intValue() - 3);
		BigInteger partOneS = BigInteger.valueOf(partOne);
		BigInteger bigPartTwoS = BigInteger.valueOf(2).pow(partTwo);
		BigInteger IntTarget = bigPartTwoS.multiply(partOneS);
		String HexTarget = new BigInteger(String.valueOf(IntTarget)).toString(16);
		//System.out.println("Numero grande: " + IntTarget);
		return HexTarget;
	}
}
