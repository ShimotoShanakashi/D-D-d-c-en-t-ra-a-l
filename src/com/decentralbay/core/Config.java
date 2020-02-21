package com.decentralbay.core;


import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class Config {	
	// set to true following line to read from decentralcore.conf
	public static boolean readfromfile = true;
	
	// default name for config file
	public static final String configfile = "decentralcore.conf";
	
	// default ip is localhost
	public static String ip = "192.168.42.104";

	// listen to port number
	public static int port = 41111;
	
	// default backlog set on 50
	public static int backlog = 50;
	
	// set tcp connections max connections
	public static int maxconnections = 2000;
	
	// print to console
	public static boolean printtoconsole = true;
	
	// output to log file
	public static boolean logtofile = true;
	
	// recover missing blocks at start
	public static boolean recoverblocks = true;
	
	// checks for missing blocks on chain
	public static boolean missingblocks = true;
	
	// checks for corrupt blocks on chain
	public static boolean corruptblocks = true;
	
	// checks for missing products on chain
	public static boolean missingproducts = true;
	
	// checks for corrupt products on chain 
	public static boolean corruptproducts = true;
	
	// recover missing blocks on chain by requesting them to peers
	public static boolean recovermissingblocks = true;

	// recover corrupt blocks on chain by requesting them to peers
	public static boolean recovercorruptblocks = true;
	
	// recover missing blocks on products by requesting them to peers
	public static boolean recovermissingproducts = true;
	
	// recover corrupt blocks on products by requesting them to peers
	public static boolean recovercorruptproducts = true;
	
	// checks for correct hash linking of blocks
	public static boolean hashlinking = true;
	
	// checks for correct hashing of block hash by rehashing them
	public static boolean hashing = true;
	
	// checks for correct hashing of merkle roots of blocks
	public static boolean merkleroots = true;
	
	// max number of blocks included on each block before automining
	public static int maxblocks = 3;
	
	// automining option
	public static boolean automining = true;

	// erase connections from file when failed
	public static boolean eraseconnections = false;
	
	
	public Config() {		
		// specifies the default name of the config file		
		if (readfromfile == true) {
			readFromFile();
		}
		
	}
	
	
	private void readFromFile() {		
		Parameters params = new Parameters();
		FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
		    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
		    .configure(params.properties()
		        .setFileName("decentralcore.conf"));
		try {
		    Configuration config = builder.getConfiguration();

		    ip = config.getString("ip");
		    port = config.getInt("port");
		    backlog = config.getInt("backlog");
			maxconnections = config.getInt("maxconnections");
			maxblocks = config.getInt("maxblocks");
			printtoconsole = config.getBoolean("printtoconsole");
			logtofile = config.getBoolean("logtofile");
			recoverblocks = config.getBoolean("recoverblocks");
			missingblocks = config.getBoolean("missingblocks");
			corruptblocks = config.getBoolean("corruptblocks");
			missingproducts = config.getBoolean("missingproducts");
			corruptproducts = config.getBoolean("corruptproducts");
			recovermissingblocks = config.getBoolean("recovermissingblocks");
			recovercorruptblocks = config.getBoolean("recovercorruptblocks");
			recovermissingproducts = config.getBoolean("recovermissingproducts");
			recovercorruptproducts = config.getBoolean("recovercorruptproducts");
			hashlinking = config.getBoolean("hashlinking");
			hashing = config.getBoolean("hashing");
			merkleroots = config.getBoolean("merkleroots");
			//System.out.println(this.toString());
		} catch(ConfigurationException cex){
		    // loading of the configuration file failed
			cex.printStackTrace();
		}
	}


	public String toString() {
		return "Current Config ->\n\nip: " + ip + "\nport: " + port + "\nbacklog: " + backlog + "\nmax connections: " + maxconnections + "\nprinttoconsole: " + printtoconsole + "\nlogtofile: " + logtofile
				+ "\nrecoverblocks: " + recoverblocks + "\nmissingblocks: " + missingblocks + "\ncorruptblocks: " + corruptblocks + "\nmissing products: " + missingproducts
				+ "\ncorrupt products: " + corruptproducts + "\nrecovermissingblocks: " + recovermissingblocks + "\nrecovercorruptblocks: " + recovercorruptblocks
				+ "\nhashlinking: " + hashlinking + "\nhashing: " + hashing + "\nmerkleroots: " + merkleroots;
	}
}
