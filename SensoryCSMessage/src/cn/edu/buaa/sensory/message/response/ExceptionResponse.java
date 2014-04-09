package cn.edu.buaa.sensory.message.response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.buaa.sensory.message.ProtocolMessage;
import cn.edu.buaa.sensory.messagevisitor.MessageVisitor;

public class ExceptionResponse extends ProtocolMessage {
	private byte exceptionCode;
	
	public ExceptionResponse() {
		
	}
	
	public ExceptionResponse(byte exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	
	public byte getExceptionCode() {
		return exceptionCode;
	}
	
	@Override
	public byte getDataType() {
		return ExceptionResponse;
	}

	@Override
	public void read(DataInputStream iStream) throws IOException {
		exceptionCode = iStream.readByte();
	}
	
	@Override
	public void write(MessageVisitor visitor, DataOutputStream oStream) throws IOException {
		super.write(visitor, oStream);
		visitor.getWriter().write(oStream, this);
	}

}
