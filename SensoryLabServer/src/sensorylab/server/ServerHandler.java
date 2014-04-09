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
		System.out.println("生成服务器事务处理器");
	}
	
	@Override
	public void handle(Socket acceptedSocket) {
		IController controller = controllerFactory.createController(acceptedSocket);
		ControllingQueue.getInstance().add(controller);
	}
}
