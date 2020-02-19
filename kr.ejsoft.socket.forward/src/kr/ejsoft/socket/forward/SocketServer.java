package kr.ejsoft.socket.forward;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketServer {
	private int listen = -1;
	private String host = null;
	private int port = -1;
	
	public SocketServer(int listen, String host, int port) {
		this.listen = listen;
		this.host = host;
		this.port = port;
	}

	public void start() {
		try {
			// 서버소켓을 생성하고 5000번 포트와 결합(bind) 시킨다.
			ServerSocket serverSocket = new ServerSocket(this.listen);
			System.out.println(getTime() + " 서버(" + this.host + ":" + this.port + ")가 준비되었습니다.");

			while (true) {
				// 서버소켓은 연결요청이 올 때까지 실행을 멈추고 기다린다.
				// System.out.println(getTime() + " 서버가 대기중입니다.");
				// 서버소켓의 연결을 허용합니다.
				Socket clientSocket = serverSocket.accept();
				
				// String clientIp = clientSocket.getInetAddress().getHostAddress();
				// IP를 통해 권한을 부여하려면 여기에 코드를 추가하십시오.
//				if(denied) {
//					try { clientSocket.close(); } catch (Exception e) { }
//					break;
//				}
				
				ClientThread clientThread = new ClientThread(clientSocket, this.host, this.port);
				clientThread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	}
}
