package cn.edu.buaa.sensory.message.request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;

import cn.edu.buaa.sensory.message.MessageException;
import cn.edu.buaa.sensory.messagevisitor.MessageVisitor;

public class DeleteAllHistoryRequest extends DeleteHistoryRequest {
	
	public DeleteAllHistoryRequest() {
		
	}
	
	public DeleteAllHistoryRequest(String authenticationString, Calendar start, Calendar end) {
		super(authenticationString, start, end);
	}
	
	@Override
	public byte getDataType() {
		return DeleteAllHistoryData;
	}

	@Override
	public void read(DataInputStream iStream) throws IOException, MessageException {
		authenticationString = iStream.readUTF();
		start = readDateTime(iStream);
		end = readDateTime(iStream);
	}
	
	@Override
	public void write(MessageVisitor visitor, DataOutputStream oStream) throws IOException {
		super.write(visitor, oStream);
		visitor.getWriter().write(oStream, this);
	}

}
