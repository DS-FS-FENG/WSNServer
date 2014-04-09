package framework.server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server implements IServer {
	private int port;
	
	protected IHandler handler;
	protected IMessagePrinter printer;
	
	public Server(int port, IHandler handler, IMessagePrinter printer){
		this.port = port;
		this.handler = handler;
		this.printer = printer;
		System.out.println("���ɷ�����");
	}
	
	public void run(){
		System.out.println("��������ʼ����");
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			while (true) {
				Socket acceptedSocket = server.accept();
				System.out.println("��������");
				handle(acceptedSocket);
			}
		} catch (Exception e) {
			print(e.getMessage());
		} finally {
			try {
				server.close();
				System.out.println("�������ر�");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void print(String message){
		printer.print(message);
	}
	
	private void handle(Socket acceptedSocket) {
		handler.handle(acceptedSocket);
	}
}
