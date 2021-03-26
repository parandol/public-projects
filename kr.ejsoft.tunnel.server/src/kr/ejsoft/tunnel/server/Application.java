package kr.ejsoft.tunnel.server;

import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		if(args.length < 4 || (!"local".equals(args[0]) && !"remote".equals(args[0]))) {
			usage();
		}
		
		String mode = args[0];
		int tunnelPort = Integer.parseInt(args[1]);
		String host = args[2];
		int port = Integer.parseInt(args[3]);
		
		new SecureRandom().nextBytes(new byte[1]);
		logger.debug("Initialized Random Number generator.");
		
		TunnelServer tunnelServer;
		if("local".equals(mode)) {
			tunnelServer = new TunnelServer(false, tunnelPort, host, port);
		} else {
			tunnelServer = new TunnelServer(true, tunnelPort, host, port);
		}
		if(tunnelServer != null) tunnelServer.listen();
	}
	
	private static void usage() {
		System.err.println("Usage");
		System.err.println("Server : java kr.ejsoft.tunnel.server.Application remote tunnelPort server-host server-port");
		System.err.println("\t-Djavax.net.ssl.keyStore=server.keystore -Djavax.net.ssl.keyStorePassword=changeit -Djavax.net.ssl.trustStore=server.keystore");

		System.err.println("Client : java kr.ejsoft.tunnel.server.Application local tunnelPort tunnel-remote-server-host listen-port");
		System.err.println("\t-Djavax.net.ssl.keyStore=client.keystore -Djavax.net.ssl.keyStorePassword=changeit -Djavax.net.ssl.trustStore=client.keystore");
		
		System.exit(1);
	}
	
}
