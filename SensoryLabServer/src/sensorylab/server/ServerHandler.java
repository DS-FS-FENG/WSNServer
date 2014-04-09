package sensorylab.server;

import java.net.Socket;

import framework.eventcontroller.ControllerFactory;
import framework.eventcontroller.IController;
import framework.server.IHandler;
import framework.utilities.ControllingQueue;

public class ServerHandler implements IHandler {
	private ControllerFactory controllerFactory;
	
	public ServerHandler(ControllerFactory controllerFactory) {
		this.controllerFactory = controllerFactory;
		System.out.println("���ɷ�������������");
	}
	
	@Override
	public void handle(Socket acceptedSocket) {
		IController controller = controllerFactory.createController(acceptedSocket);
		ControllingQueue.getInstance().add(controller);
	}
}
