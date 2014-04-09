package sensorylab.processer.event;

import sensorylab.processer.SensoryDataBaseProcessor;
import utilities.Utilities;
import framework.eventcontroller.IClientRequest;
import framework.processer.DataBaseProcessor;
import framework.processer.IOStreamProcessor;
import framework.processer.event.IEvent;
import framework.processer.event.IEventFactory;

public class SensoryLabEventFactory implements IEventFactory {
	private DataBaseProcessor dbProcessor;
	private IOStreamProcessor tinyOSIOProcessor;
	private IOStreamProcessor csIOProcessor;
	
	public SensoryLabEventFactory(DataBaseProcessor dbProcessor) {
		this.dbProcessor = dbProcessor;
		System.out.println("生成事件工厂");
	}
	
	public void setTinyOSIOProcessor(IOStreamProcessor tinyOSIOProcessor) {
		this.tinyOSIOProcessor = tinyOSIOProcessor;
	}
	
	public void setCSIOProcessor(IOStreamProcessor csIOProcessor) {
		this.csIOProcessor = csIOProcessor;
	}
	
	@Override
	public IEvent createEvent(IClientRequest request) {
		System.out.println("生成事件");
		IEvent event = null;
		switch (request.getRequestType()) {
		case Utilities.WSNNetgateRequest:
			event = new GatewayEvent(tinyOSIOProcessor, (SensoryDataBaseProcessor)dbProcessor);
			event.setStream(request.getInputStream(), request.getOutputStream());
			event.setSource(request.getRequestSource());
			break;
		case Utilities.ClientRequest:
			event = new ClientEvent(csIOProcessor, (SensoryDataBaseProcessor)dbProcessor);
			event.setStream(request.getInputStream(), request.getOutputStream());
			event.setSource(request.getRequestSource());
			break;
		case Utilities.Exception:
			event = new UnknownSourceEvent();
			event.setStream(request.getInputStream(), request.getOutputStream());
			break;
		}
		
		return event;
	}

}
