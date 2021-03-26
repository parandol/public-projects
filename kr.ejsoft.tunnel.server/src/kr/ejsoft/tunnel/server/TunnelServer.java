package kr.ejsoft.tunnel.server;

import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.Arrays;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class TunnelServer {
	private static final Logger logger = LoggerFactory.getLogger(TunnelServer.class);

	private boolean isRemote = false;
	private final String remoteHost;
	private final int remotePort;
	private final int tunnelPort;

	public TunnelServer(boolean isRemote, int tunnelPort, String remoteHost, int remotePort) {
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
		this.tunnelPort = tunnelPort;
		this.isRemote = isRemote;
	}

	public void listen() {
		try {
//			ServerSocket serverSocket = new ServerSocket(port);
			ServerSocket serverSocket = makeServerSocket();
			logger.info("listening...");

			while (true) {
				Socket socket = serverSocket.accept();
				startThread(new TunnelConnection(socket, isRemote, tunnelPort, remoteHost, remotePort));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void startThread(TunnelConnection connection) {
		Thread t = new Thread(connection);
		t.start();
	}

	private ServerSocket makeServerSocket() {
		try {
			if (this.isRemote) {
				SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
				
				SSLServerSocket serverSocket = (SSLServerSocket) factory.createServerSocket(this.tunnelPort);
//				serverSocket.setEnabledProtocols(new String[] {"TLSv1", "SSLv3"});
//				serverSocket.setEnabledProtocols(new String[] {"TLSv1", "TLSv1.1", "TLSv1.2", "SSLv3"});
				serverSocket.setEnabledProtocols(new String[] {"TLSv1.2"});
				serverSocket.setEnabledCipherSuites(factory.getSupportedCipherSuites());
				serverSocket.setNeedClientAuth(true);
				
				logger.debug("CipherSuites : {}", Arrays.toString(serverSocket.getEnabledCipherSuites()));
				return serverSocket;
				
				
//				TrustManager[] trustAllCerts = new TrustManager[] {
//			            new X509TrustManager() {
//			                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
//			                }
//
//			                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
//			                }
//
//			                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//			                    return null;
//			                }
//			            }
//			    };
//
//			    // Install the all-trusting trust manager
//			    SSLContext sc = SSLContext.getInstance("TLSv1.2");
//			    sc.init(null, trustAllCerts, new SecureRandom());
//			    
//			    SSLServerSocket ssl = (SSLServerSocket) sc.getServerSocketFactory().createServerSocket(this.tunnelPort);
//			    // Got rid of:
//			    //ssl.setEnabledCipherSuites(sc.getServerSocketFactory().getSupportedCipherSuites());
////			    ssl.setEnabledProtocols(new String[] {"TLSv1", "TLSv1.1", "TLSv1.2", "SSLv3"});
//			    ssl.setEnabledProtocols(new String[] {"TLSv1.2"});
//				ssl.setNeedClientAuth(true);
//
//				logger.debug("CipherSuites : {}", Arrays.toString(ssl.getEnabledCipherSuites()));
//
//				return ssl;
			} else {
				return ServerSocketFactory.getDefault().createServerSocket(this.remotePort);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}