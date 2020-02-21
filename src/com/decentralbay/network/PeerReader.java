package com.decentralbay.network;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class PeerReader {
	public static ArrayList<String> getPool() throws IOException {
		ArrayList<String> ippool = new ArrayList<String>();

		BufferedReader br = new BufferedReader(new FileReader("decentralbay.plist"));
		String line = null;

		while ((line = br.readLine()) != null) {
			if (line.substring(0, 8).equals("127.0.0.") || line.substring(0, 9).equals("192.168.1")) {
	    		if(!line.equals(InetAddress.getLocalHost().getHostAddress())) {
	    			ippool.add(line);
	    		}
			}
		}
		br.close();
		return ippool;
	}

	public static void removeDups() throws IOException {
		// PrintWriter object for output.txt

		// BufferedReader object for input.txt
		BufferedReader br1 = new BufferedReader(new FileReader("decentralbay.plist"));

		String line1 = br1.readLine();
		int countLines = 0;
		// loop for each line of input.txt
		while (line1 != null) {
			br1.read();
			countLines++;
		}
		//System.out.println("onlines: " + countLines);
		// closing resources
		br1.close();
		System.out.println("File operation performed successfully");
	}
}
