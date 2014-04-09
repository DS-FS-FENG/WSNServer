package cn.edu.buaa.sensory.message.response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.buaa.sensory.message.MessageException;
import cn.edu.buaa.sensory.messagevisitor.MessageVisitor;

public class ReadActivedNodesResponse extends ReadResponse {
	public final static int UnitLength = 3;
	
	public ReadActivedNodesResponse() {
		
	}
	
	public ReadActivedNodesResponse(Object[] datas) {
		super(datas);
	}
	
	@Override
	public byte getDataType() {
		return ReadActivedNodes;
	}

	@Override
	public void read(DataInputStream iStream) throws IOException, MessageException {
		dataNumber = iStream.readByte();
		datas = new Object[dataNumber * UnitLength];
		for(int i = 0; i < dataNumber; i++) {
			int index = i * UnitLength;
			datas[index++] = iStream.readInt();
			datas[index++] = readDateTime(iStream);
			datas[index] = iStream.readInt();
		}
	}
	
	@Override
	public void write(MessageVisitor visitor, DataOutputStream oStream) throws IOException {
		super.write(visitor, oStream);
		visitor.getWriter().write(oStream, this);
	}

	@Override
	protected int getUnitLength() {
		return UnitLength;
	}

}
