package com.decentralbay.net;

import java.io.IOException;
import com.decentralbay.core.Config;
import com.decentralbay.network.ServerManager;

public interface networking {
	public default ServerManager getNetwork() throws IOException {
		// implements - config file on port and timeout
		// int port, int backlog, String ip
		ServerManager connectionManager = ServerManager.getServerManagerSingleton(Config.port, Config.backlog, Config.ip);
		return connectionManager;
	}
	
	
	public default void broadcastMessage(String message) {
		new Thread(() -> {
			try {
				getNetwork().broadcastMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}
	
	
	public default String requestMessage(String message) {
		return "";
	}
}
