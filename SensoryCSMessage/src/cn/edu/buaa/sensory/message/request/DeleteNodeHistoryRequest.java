package cn.edu.buaa.sensory.message.request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;

import cn.edu.buaa.sensory.message.MessageException;
import cn.edu.buaa.sensory.messagevisitor.MessageVisitor;

public class DeleteNodeHistoryRequest extends DeleteHistoryRequest {
	private int nodeId;
	
	public DeleteNodeHistoryRequest() {
		
	}
	
	public DeleteNodeHistoryRequest(String authenticationString, Calendar start, Calendar end, int nodeId) {
		super(authenticationString, start, end);
		this.nodeId = nodeId;
	}
	
	public int getNodeId() {
		return nodeId;
	}
	
	@Override
	public byte getDataType() {
		return DeleteNodeHistoryData;
	}

	@Override
	public void read(DataInputStream iStream) throws IOException, MessageException {
		authenticationString = iStream.readUTF();
		nodeId = iStream.readInt();
		start = readDateTime(iStream);
		end = readDateTime(iStream);
	}
	
	@Override
	public void write(MessageVisitor visitor, DataOutputStream oStream)
			throws IOException {
		super.write(visitor, oStream);
		visitor.getWriter().write(oStream, this);
	}
}
