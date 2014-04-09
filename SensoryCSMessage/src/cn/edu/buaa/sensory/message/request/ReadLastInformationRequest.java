package cn.edu.buaa.sensory.message.request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;

import cn.edu.buaa.sensory.message.MessageException;
import cn.edu.buaa.sensory.message.ProtocolMessage;
import cn.edu.buaa.sensory.messagevisitor.MessageVisitor;

public class ReadLastInformationRequest extends ProtocolMessage {
	private Calendar start;
	private int[] nodeIds;
	
	public ReadLastInformationRequest() {
		
	}
	
	public ReadLastInformationRequest(Calendar start, int[] nodeIds) {
		this.start = start;
		this.nodeIds = nodeIds;
	}
	
	public Calendar getStart() {
		return start;
	}
	
	public int[] getNodeIds() {
		return nodeIds;
	}
	
	@Override
	public byte getDataType() {
		return ReadLastInformation;
	}

	@Override
	public void read(DataInputStream iStream) throws IOException, MessageException {
		start = readDateTime(iStream);
		byte idNumber = iStream.readByte();
		nodeIds = new int[idNumber];
		for(int i = 0; i < idNumber; i++)
			nodeIds[i] = iStream.readInt();
	}
	
	@Override
	public void write(MessageVisitor visitor, DataOutputStream oStream) throws IOException {
		super.write(visitor, oStream);
		visitor.getWriter().write(oStream, this);
	}

}
