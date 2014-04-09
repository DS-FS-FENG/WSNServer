package cn.edu.buaa.sensory.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.edu.buaa.sensory.messagevisitor.MessageVisitor;

public abstract class ProtocolMessage implements IProtocolMessage {
	protected final static DateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void write(MessageVisitor visitor, DataOutputStream oStream) throws IOException {
		oStream.writeByte(ProtocolHead);
		oStream.writeByte(getDataType());
	}
	
	protected static Calendar readDateTime(DataInputStream iStream) throws MessageException {
		Calendar c = Calendar.getInstance();
		try {
			Date date = DateFormat.parse(iStream.readUTF());
			c.setTime(date);
		} catch (Exception e) {
			throw new MessageException(IProtocolMessage.ParseFailue);
		}
		return c;
	}
}
