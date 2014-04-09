package cn.edu.buaa.sensory.message.response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.buaa.sensory.message.MessageException;
import cn.edu.buaa.sensory.messagevisitor.MessageVisitor;

public class ReadNodeHistoryResponse extends ReadResponse {
	public final static int UnitLength = 11;
	
	private int nodeId;
	
	public ReadNodeHistoryResponse() {
		
	}
	
	public ReadNodeHistoryResponse(int nodeId, Object[] datas) {
		super(datas);
		this.nodeId = nodeId;
	}
	
	public int getNodeId() {
		return nodeId;
	}
	
	@Override
	public byte getDataType() {
		return ReadNodeHistoryData;
	}

	@Override
	public void read(DataInputStream iStream) throws IOException, MessageException {
		nodeId = iStream.readInt();
		dataNumber = iStream.readByte();
		datas = new Object[dataNumber * UnitLength];
		for(int i = 0; i < dataNumber ; i++) {
			int index = i * UnitLength;
			datas[index++] = readDateTime(iStream);
			datas[index++] = iStream.readDouble();
			datas[index++] = iStream.readBoolean();
			datas[index++] = iStream.readDouble();
			datas[index++] = iStream.readBoolean();
			datas[index++] = iStream.readDouble();
			datas[index++] = iStream.readDouble();
			datas[index++] = iStream.readDouble();
			datas[index++] = iStream.readBoolean();
			datas[index++] = iStream.readDouble();
			datas[index] = iStream.readBoolean();
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
