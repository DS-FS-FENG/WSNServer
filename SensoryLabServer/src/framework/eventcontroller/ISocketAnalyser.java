package framework.eventcontroller;

import java.net.Socket;

public interface ISocketAnalyser {
	public IClientRequest analyse(Socket acceptedSocket);
}
