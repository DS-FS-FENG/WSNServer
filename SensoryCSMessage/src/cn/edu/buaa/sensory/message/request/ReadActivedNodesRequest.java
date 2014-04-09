package cn.edu.buaa.sensory.message.request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;

import cn.edu.buaa.sensory.message.MessageException;
import cn.edu.buaa.sensory.message.ProtocolMessage;
import cn.edu.buaa.sensory.messagevisitor.MessageVisitor;

public class ReadActivedNodesRequest extends ProtocolMessage {
	private Calendar start;
	
	public ReadActivedNodesRequest() {
		
	}
	
	public ReadActivedNodesRequest(Calendar start) {
		this.start = start;
	}
	
	public Calendar getStart() {
		return start;
	}
	
	@Override
	public byte getDataType() {
		return ReadActivedNodes;
	}

	@Override
	public void read(DataInputStream iStream) throws IOException, MessageException {
		start = readDateTime(iStream);
	}
	
	@Override
	public void write(MessageVisitor visitor, DataOutputStream oStream) throws IOException {
		super.write(visitor, oStream);
		visitor.getWriter().write(oStream, this);
	}

}
