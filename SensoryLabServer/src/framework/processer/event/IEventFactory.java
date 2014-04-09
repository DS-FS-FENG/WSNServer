package framework.processer.event;

import framework.eventcontroller.IClientRequest;

public interface IEventFactory {
	public IEvent createEvent(IClientRequest request);
}
