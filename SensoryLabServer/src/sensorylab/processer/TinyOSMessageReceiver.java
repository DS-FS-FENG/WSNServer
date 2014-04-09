package sensorylab.processer;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TinyOSMessageReceiver implements Runnable {
	private final static int port = 9004;
	private ServerSocket serverSocket;
	private DataOutputStream tinyOSInterface;
	
	public TinyOSMessageReceiver() {
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			Socket socket = serverSocket.accept();
			tinyOSInterface = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e){
			e.printStackTrace();
			try {
				serverSocket.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public DataOutputStream getTinyOSInterface() {
		return tinyOSInterface;
	}
}
