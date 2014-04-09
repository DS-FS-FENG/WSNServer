package cn.edu.buaa.sensory.message.response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.buaa.sensory.message.ProtocolMessage;
import cn.edu.buaa.sensory.messagevisitor.MessageVisitor;

public class DeleteHistoryResponse extends ProtocolMessage {
	private byte functionType;
	private boolean isSucceeded;
	
	public DeleteHistoryResponse(byte functionType) {
		this.functionType = functionType;
	}
	
	public DeleteHistoryResponse(byte functionType, boolean isSucceeded) {
		this(functionType);
		this.isSucceeded = isSucceeded;
	}
	
	public boolean getIsSucceeded() {
		return isSucceeded;
	}
	
	@Override
	public byte getDataType() {
		return functionType;
	}

	@Override
	public void read(DataInputStream iStream) throws IOException {
		isSucceeded = iStream.readBoolean();
	}
	
	@Override
	public void write(MessageVisitor visitor, DataOutputStream oStream) throws IOException {
		super.write(visitor, oStream);
		visitor.getWriter().write(oStream, this);
	}

}
