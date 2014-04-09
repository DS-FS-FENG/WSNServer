package cn.edu.buaa.sensory.message.response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.buaa.sensory.message.Permission;
import cn.edu.buaa.sensory.message.ProtocolMessage;
import cn.edu.buaa.sensory.messagevisitor.MessageVisitor;

public class AuthenticationResponse extends ProtocolMessage {
	
	private byte permission;
	private String authenticationString;
	
	public AuthenticationResponse() {
		
	}
	
	public AuthenticationResponse(byte permission) {
		this.permission = permission;
	}
	
	public AuthenticationResponse(byte permission, String authenticationString) {
		this(permission);
		this.authenticationString = authenticationString;
	}
	
	public byte getPermission() {
		return permission;
	}
	
	public String getAuthenticationString() {
		if(permission != Permission.Admin)
			return null;
		
		return authenticationString;
	}
	
	@Override
	public byte getDataType() {
		return Authentication;
	}

	@Override
	public void read(DataInputStream iStream) throws IOException {
		permission = iStream.readByte();
		if (permission == Permission.Admin)
			authenticationString = iStream.readUTF();
		else
			authenticationString = null;
	}
	
	@Override
	public void write(MessageVisitor visitor, DataOutputStream oStream) throws IOException {
		super.write(visitor, oStream);
		visitor.getWriter().write(oStream, this);
	}

}
