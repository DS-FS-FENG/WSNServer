
import sensorylab.eventcontroller.SensoryLabControllerFactory;
import sensorylab.eventcontroller.SensorySocketAnalyser;
import sensorylab.processer.CSMessageImpl;
import sensorylab.processer.SensoryDataBaseProcessor;
import sensorylab.processer.SensoryIOStreamProcessor;
import sensorylab.processer.TinyOSMessageImpl;
import sensorylab.processer.event.SensoryLabEventFactory;
import sensorylab.server.ServerHandler;
import sensorylab.server.SimplePrinter;
import framework.eventcontroller.ControllerFactory;
import framework.eventcontroller.ISocketAnalyser;
import framework.processer.DataBaseProcessor;
import framework.processer.IMessageImpl;
import framework.processer.IOStreamProcessor;
import framework.processer.ISqlImpl;
import framework.processer.SqlImpl;
import framework.server.IHandler;
import framework.server.IMessagePrinter;
import framework.server.IServer;
import framework.server.Server;
import framework.utilities.ControllingQueue;


public class Main {

	public static void main(String[] args) {
		ControllingQueue.getInstance();
		ISqlImpl sqlImpl = new SqlImpl();
		IMessageImpl tinyOSImpl = new TinyOSMessageImpl();
		IMessageImpl csImpl = new CSMessageImpl();
		DataBaseProcessor dbProcessor = new SensoryDataBaseProcessor(sqlImpl);
		IOStreamProcessor tinyOSIOProcessor = new SensoryIOStreamProcessor(tinyOSImpl);
		IOStreamProcessor csIOProcessor = new SensoryIOStreamProcessor(csImpl);
		SensoryLabEventFactory eventFatory = new SensoryLabEventFactory(dbProcessor);
		eventFatory.setTinyOSIOProcessor(tinyOSIOProcessor);
		eventFatory.setCSIOProcessor(csIOProcessor);
		ISocketAnalyser analyser = new SensorySocketAnalyser();
		ControllerFactory controllerFactory = new SensoryLabControllerFactory(analyser, eventFatory);
		IHandler handler = new ServerHandler(controllerFactory);
		IMessagePrinter printer = new SimplePrinter();
		IServer server = new Server(8080, handler, printer);
		controllerFactory.setServer(server);
		server.run();
	}

}
