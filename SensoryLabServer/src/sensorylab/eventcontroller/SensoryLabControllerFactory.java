package sensorylab.eventcontroller;

import framework.eventcontroller.ControllerFactory;
import framework.eventcontroller.IController;
import framework.eventcontroller.ISocketAnalyser;
import framework.processer.event.IEvent;
import framework.processer.event.IEventFactory;
import framework.server.IServer;

public class SensoryLabControllerFactory extends ControllerFactory {
	
	public SensoryLabControllerFactory(ISocketAnalyser analyser, IEventFactory eventFatory) {
		super(analyser, eventFatory);
		System.out.println("生成控制器工厂");
	}

	@Override
	public IController createController(IServer server, IEvent event) {
		return new SenseryLabController(server, event);
	}
	
}
