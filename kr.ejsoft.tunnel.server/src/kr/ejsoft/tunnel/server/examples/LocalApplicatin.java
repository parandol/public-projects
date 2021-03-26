package kr.ejsoft.tunnel.server.examples;

import kr.ejsoft.tunnel.server.TunnelServer;

public class LocalApplicatin {
	public static void main(String[] args) {
		String host = "localhost";
		int port = 13306;
		int tunnelPort = 23306;
		
//		new TunnelServer1(
//				host
//				, port
//				, tunnelPort
//				, false
//		);

		System.setProperty("javax.net.ssl.keyStore", "client.keystore");
		System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
		System.setProperty("javax.net.ssl.trustStore", "client.keystore");
		TunnelServer tunnelServer = new TunnelServer(false, tunnelPort, host, port);
		tunnelServer.listen();
	}
}
