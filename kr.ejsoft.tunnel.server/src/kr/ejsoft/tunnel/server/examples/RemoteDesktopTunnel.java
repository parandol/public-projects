package kr.ejsoft.tunnel.server.examples;

import kr.ejsoft.tunnel.server.TunnelServer;

public class RemoteDesktopTunnel {
	public static void main(String[] args) {
//		System.setProperty("javax.net.debug", "all");
		System.setProperty("https.protocols", "TLSv1.2");
		System.setProperty("javax.net.ssl.keyStore", "client.keystore");
		System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
		System.setProperty("javax.net.ssl.trustStore", "cacerts.keystore");
		TunnelServer tunnelServer = new TunnelServer(false, 53389, "192.168.0.100", 53389);
		tunnelServer.listen();
	}
}
