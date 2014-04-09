package cn.edu.buaa.sensory.message.request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;

import cn.edu.buaa.sensory.message.MessageException;
import cn.edu.buaa.sensory.message.ProtocolMessage;
import cn.edu.buaa.sensory.messagevisitor.MessageVisitor;

public class ReadNodesHistoryErrorRequest extends ProtocolMessage {
	private int[] nodeIds;
	private Calendar start;
	private Calendar end;
	
	public ReadNodesHistoryErrorRequest() {
		
	}
	
	public ReadNodesHistoryErrorRequest(int[] nodeIds, Calendar start, Calendar end) {
		if(start.after(end))
			throw new IllegalArgumentException("起始时间不能晚于结束时间");
		
		this.nodeIds = nodeIds;
		this.start = start;
		this.end = end;
	}
	
	public int[] getNodeIds() {
		return nodeIds;
	}
	
	public Calendar getStart() {
		return start;
	}
	
	public Calendar getEnd() {
		return end;
	}

	@Override
	public byte getDataType() {
		return ReadNodesHistoryError;
	}

	@Override
	public void read(DataInputStream iStream) throws IOException, MessageException {
		byte idNumber = iStream.readByte();
		nodeIds = new int[idNumber];
		for(int i = 0; i < idNumber; i++)
			nodeIds[i] = iStream.readInt();
		start = readDateTime(iStream);
		end = readDateTime(iStream);
	}
	
	@Override
	public void write(MessageVisitor visitor, DataOutputStream oStream) throws IOException {
		super.write(visitor, oStream);
		visitor.getWriter().write(oStream, this);
	}

}
