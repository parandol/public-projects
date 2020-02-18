package kr.ejsoft.socket.forward;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class SocketClient {

	public static void main(String[] args) {
		try {
			String serverIP = "127.0.0.1"; // 127.0.0.1 & localhost 본인
			System.out.println("서버에 연결중입니다. 서버 IP : " + serverIP);

			// 소켓을 생성하여 연결을 요청한다.
			Socket socket = new Socket(serverIP, 5000);

			// 소켓으로 부터 받은 데이터를 출력한다.
			System.out.println("연결을 종료합니다.");
			socket.close();
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} // try - catch
	} // main

}
