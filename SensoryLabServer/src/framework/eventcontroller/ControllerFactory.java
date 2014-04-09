package framework.eventcontroller;

import java.net.Socket;

import framework.processer.event.IEvent;
import framework.processer.event.IEventFactory;
import framework.server.IServer;

public abstract class ControllerFactory {
	private ISocketAnalyser analyser;
	private IServer server;
	private IEventFactory eventFactory;
	
	public ControllerFactory(ISocketAnalyser analyser, IEventFactory eventFactory){
		this.analyser = analyser;
		this.eventFactory = eventFactory;
	}
	
	public void setServer(IServer server) {
		this.server = server;
	}
	
	public IController createController(Socket acceptedSocket){
		IController controller = createController(server, eventFactory.createEvent(analyser.analyse(acceptedSocket)));
		System.out.println("´´½¨¿ØÖÆÆ÷");
		return controller;
	}
	
	public abstract IController createController(IServer server, IEvent event);
}
