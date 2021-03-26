package kr.ejsoft.tunnel.server;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TunnelConnection implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(TunnelConnection.class);

	private boolean isRemote = false;
	private final Socket clientsocket;
	private final String remoteHost;
	private final int remotePort;
	private final int tunnelPort;
	private Socket serverConnection = null;

	public TunnelConnection(Socket clientsocket, boolean isRemote, int tunnelPort, String remoteHost, int remotePort) {
		this.isRemote = isRemote;
		this.clientsocket = clientsocket;
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
		this.tunnelPort = tunnelPort;
	}

	@Override
	public void run() {
		logger.info("new connection {}:{}", clientsocket.getInetAddress().getHostName(), clientsocket.getPort());
		try {
//			serverConnection = new Socket(host, remotePort);
			serverConnection = connect();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		logger.info("Proxy {}:{} <-> {}:{}", clientsocket.getInetAddress().getHostName(), clientsocket.getPort(), serverConnection.getInetAddress().getHostName(), serverConnection.getPort());

		new Thread(new TunnelStream(clientsocket, serverConnection)).start();
		new Thread(new TunnelStream(serverConnection, clientsocket)).start();
		new Thread(() -> {
			while (true) {
				if (clientsocket.isClosed()) {
					logger.info("client socket ({}:{}) closed", clientsocket.getInetAddress().getHostName(), clientsocket.getPort());
					closeServerConnection();
					break;
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignored) {
				}
			}
		}).start();
	}

	private void closeServerConnection() {
		if (serverConnection != null && !serverConnection.isClosed()) {
			try {
				logger.info("closing remote host connection {}:{}", serverConnection.getInetAddress().getHostName(), serverConnection.getPort());
				serverConnection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Socket connect() throws UnknownHostException, IOException {
		if(this.isRemote) {
			Socket socket = new Socket(this.remoteHost, this.remotePort);
			return socket;
		} else {
			SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket socket = (SSLSocket) factory.createSocket(this.remoteHost, this.tunnelPort);
			socket.setEnabledProtocols(new String[] { "TLSv1.2" });
			return socket;
		}
	}
}