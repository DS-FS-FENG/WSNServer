package cn.edu.buaa.sensory.messagevisitor;

import java.io.DataInputStream;

import cn.edu.buaa.sensory.message.IProtocolMessage;
import cn.edu.buaa.sensory.message.MessageException;

public abstract class MessageVisitor {
	protected IMessageWriter writer;
	
	public MessageVisitor(IMessageWriter writer) {
		this.writer = writer;
	}
	
	public static MessageVisitor getInstance(){
		return new SensoryMessageVisitor(new SensoryMessageWriter());
	}
	
	public IMessageWriter getWriter() {
		return writer;
	}
	
	public abstract IProtocolMessage readRequest(DataInputStream iStream) throws MessageException;
	
	public abstract IProtocolMessage readResponse(DataInputStream iStream) throws MessageException;
}
