package cn.edu.buaa.sensory.message.request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;

import cn.edu.buaa.sensory.message.MessageException;
import cn.edu.buaa.sensory.message.ProtocolMessage;
import cn.edu.buaa.sensory.messagevisitor.MessageVisitor;

public class ReadNodeHistoryRequest extends ProtocolMessage {
	private int nodeId;
	private Calendar start;
	private Calendar end;
	
	public ReadNodeHistoryRequest() {
		
	}
	
	public ReadNodeHistoryRequest(int nodeId, Calendar start, Calendar end) {
		if(start.after(end))
			throw new IllegalArgumentException("起始时间不能晚于结束时间");
		
		this.nodeId = nodeId;
		this.start = start;
		this.end = end;
	}
	
	public int getNodeId() {
		return nodeId;
	}
	
	public Calendar getStart() {
		return start;
	}
	
	public Calendar getEnd() {
		return end;
	}
	
	@Override
	public byte getDataType() {
		return ReadNodeHistoryData;
	}

	@Override
	public void read(DataInputStream iStream) throws IOException, MessageException {
		nodeId = iStream.readInt();
		start = readDateTime(iStream);
		end = readDateTime(iStream);
	}
	
	@Override
	public void write(MessageVisitor visitor, DataOutputStream oStream) throws IOException {
		super.write(visitor, oStream);
		visitor.getWriter().write(oStream, this);
	}

}
