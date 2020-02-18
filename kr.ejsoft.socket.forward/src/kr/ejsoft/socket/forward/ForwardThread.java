package kr.ejsoft.socket.forward;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ForwardThread extends Thread {

	private static final int BUFFER_SIZE = 8192;

	InputStream mInputStrean;
	OutputStream mOutPutStream;
	ClientThread mParent;

	public ForwardThread(ClientThread aParent, InputStream aInputStream, OutputStream aOutPutStream) {
		mParent = aParent;
		mInputStrean = aInputStream;
		mOutPutStream = aOutPutStream;
	}

	@Override
	public void run() {

		byte[] buffer = new byte[BUFFER_SIZE];

		try {
			while (true) {
				int bytesRead = mInputStrean.read(buffer);
				if (bytesRead == -1) {
					break;
				}
				mOutPutStream.write(buffer, 0, bytesRead);
				mOutPutStream.flush();
			}
		} catch (IOException e) {
		}
		mParent.connectionBroket();
	}

}
