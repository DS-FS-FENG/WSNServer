package framework.server;

import java.net.Socket;

public interface IHandler {
	public void handle(Socket acceptedSocket);
}
 