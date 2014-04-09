package sensorylab.processer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.edu.buaa.sensory.message.IProtocolMessage;
import cn.edu.buaa.sensory.message.MessageException;
import cn.edu.buaa.sensory.messagevisitor.MessageVisitor;
import sensorylab.processer.data.ClientData;
import sensorylab.processer.data.SensoryExceptionData;
import framework.processer.IMessageImpl;
import framework.processer.data.IData;

public class CSMessageImpl implements IMessageImpl {
	
	private MessageVisitor messageVisitor;
	
	public CSMessageImpl() {
		messageVisitor = messageVisitor.getInstance();
		System.out.println("生成C/S数据包执行器");
	}
	
	@Override
	public byte[] readBytes(InputStream iStream) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IData unpackage(InputStream iStream){
		System.out.println("解析C/S报文");
		IData data;
		try {
			DataInputStream dStream = new DataInputStream(iStream);
			data = new ClientData(messageVisitor.readRequest(dStream));
		} catch (MessageException e) {
			data = new SensoryExceptionData(e.getExceptionCode());
		}
		return data;
	}

	@Override
	public IData unpackage(byte[] packageBytes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeData(IData data, OutputStream oStream) throws IOException {
		DataOutputStream dStream = new DataOutputStream(oStream);
		IProtocolMessage msg = ((ClientData)data).getProtocolMessage();
		msg.write(messageVisitor, dStream);
	}

}
