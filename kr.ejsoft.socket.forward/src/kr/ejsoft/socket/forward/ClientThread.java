package kr.ejsoft.socket.forward;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThread extends Thread {
	private final Socket clientSocket;
	private Socket serverSocket;
	private boolean forwardingActive = false;
	private String host = null;
	private int port = -1;

	public ClientThread(Socket clientSocket, String host, int port) {
		this.clientSocket = clientSocket;
		this.host = host;
		this.port = port;
	}

	@Override
	public void run() {
		InputStream clientInput;
		OutputStream clinetOutput;

		InputStream serverInput;
		OutputStream servierOutPut;

		try {
			serverSocket = new Socket(this.host, this.port);
			serverSocket.setKeepAlive(true);
			clientSocket.setKeepAlive(true);

			clientInput = clientSocket.getInputStream();
			clinetOutput = clientSocket.getOutputStream();

			serverInput = serverSocket.getInputStream();
			servierOutPut = serverSocket.getOutputStream();
		} catch (Exception e) {
			System.out.println("Can not Connect to " + this.host + ":" + this.port);
			connectionBroket();
			return;
		}

		forwardingActive = true;
		ForwardThread clientForward = new ForwardThread(this, clientInput, servierOutPut);
		clientForward.start();

		ForwardThread serverForward = new ForwardThread(this, serverInput, clinetOutput);
		serverForward.start();

		System.out.println(
				"TCP Forwarding "
				+ clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort()
				+ " <--> "
				+ serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getPort()
				+ " START");
	}

	public void connectionBroket() {
		try { serverSocket.close(); } catch (Exception e) { }
		try { clientSocket.close(); } catch (Exception e) { }

		if (forwardingActive) {
			System.out.println(
					"TCP Forwarding "
					+ clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort()
					+ " <--> "
					+ serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getPort()
					+ " STOP");
			forwardingActive = false;
		}
	}
}
