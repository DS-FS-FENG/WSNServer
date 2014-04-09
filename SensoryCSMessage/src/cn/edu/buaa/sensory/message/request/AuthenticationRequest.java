package cn.edu.buaa.sensory.message.request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.buaa.sensory.message.ProtocolMessage;
import cn.edu.buaa.sensory.messagevisitor.MessageVisitor;

public class AuthenticationRequest extends ProtocolMessage {
	public final static byte LogIn = 0x01;
	public final static byte Register = 0x02;
	public final static byte LogOff = 0x03;
	
	private byte operationType;
	private String username;
	private String password;
	
	public AuthenticationRequest() {
	}
	
	public AuthenticationRequest(byte operationType, String username, String password) {
		this.operationType = operationType;
		this.username = username;
		this.password = password;
	}
	
	public byte getOperationType() {
		return operationType;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	@Override
	public byte getDataType() {
		return Authentication;
	}

	@Override
	public void read(DataInputStream iStream) throws IOException {
		operationType = iStream.readByte();
		username = iStream.readUTF();
		password = iStream.readUTF();
	}
	
	@Override
	public void write(MessageVisitor visitor, DataOutputStream oStream) throws IOException {
		super.write(visitor, oStream);
		visitor.getWriter().write(oStream, this);
	}
}
