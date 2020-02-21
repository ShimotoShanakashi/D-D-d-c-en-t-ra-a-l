package com.decentralbay.leveldb;

import org.iq80.leveldb.Options;

public class DatabaseConfig {
	private static long cacheSize = 1048576;
	private static boolean createIfMissing = true;
	
	public static Options getConfig() {
		Options config = new Options();
		config.cacheSize(100 * cacheSize);
		config.createIfMissing(createIfMissing);
		return config;
	}
}
