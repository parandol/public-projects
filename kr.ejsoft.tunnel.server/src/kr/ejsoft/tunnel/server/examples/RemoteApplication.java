package kr.ejsoft.tunnel.server.examples;

import kr.ejsoft.tunnel.server.TunnelServer;

public class RemoteApplication {
	public static void main(String[] args) {
		String host = "jemini.ipdisk.co.kr";
		int port = 13306;
		int tunnelPort = 23306;
		
//		new TunnelServer1(
//				host
//				, port
//				, tunnelPort
//				, true
//		);

		System.setProperty("https.protocols", "TLSv1.2");
		System.setProperty("javax.net.ssl.keyStore", "server.keystore");
		System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
		System.setProperty("javax.net.ssl.trustStore", "server.keystore");
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		
		TunnelServer tunnelServer = new TunnelServer(true, 53389, host, port);
		tunnelServer.listen();
	}
}
