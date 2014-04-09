package sensorylab.processer;

import java.io.InputStream;
import java.io.OutputStream;

import sensorylab.processer.data.SensoryTinyOSData;
import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PhoenixSource;
import net.tinyos.util.PrintStreamMessenger;
import framework.processer.IMessageImpl;
import framework.processer.data.IData;

public class TinyOSMessageImpl implements IMessageImpl {
	private final static int AMValue = 0xAB;
	
	private TinyOSMessageReceiver messageReceiver = new TinyOSMessageReceiver();
	private PhoenixSource pSource = BuildSource.makePhoenix("network@localhost:9004", PrintStreamMessenger.err);
	private MoteIF moteIF = new MoteIF(pSource);
	private Message msg = new Message(1);
	
	public TinyOSMessageImpl() {
		msg.amTypeSet(AMValue);
		System.out.println("生成TinyOS数据包执行器");
	}
	
	@Override
	public byte[] readBytes(InputStream iStream) {
		TinyOSMessageListener listener = new TinyOSMessageListener(this);
		try{
			synchronized (this) {
				listener.DataBytes = null;
				moteIF.registerListener(msg, listener);
				byte[] readBytes = new byte[1024];
				int readNum;
				do {
					readNum = iStream.read(readBytes);
					messageReceiver.getTinyOSInterface().write(readBytes, 0, readNum);
				} while (readBytes[readNum - 1] != 0x7E);
				this.wait(2000);
				return listener.DataBytes;
			}
		} catch(Exception e){
			e.printStackTrace();
			return null;
		} finally {
			moteIF.deregisterListener(msg, listener);
		}
	}

	@Override
	public IData unpackage(InputStream iStream) {
		System.out.println("解析TinyOS报文");
		IData data = new SensoryTinyOSData();
		byte[] bytes = readBytes(iStream);
		if(bytes == null)
			data = null;
		else
			data.Initialize(bytes);
		return data;
	}

	@Override
	public IData unpackage(byte[] packageBytes) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class TinyOSMessageListener implements MessageListener {
		byte[] DataBytes;
		TinyOSMessageImpl impl;
		
		private TinyOSMessageListener(TinyOSMessageImpl impl) {
			this.impl = impl;
		}
		
		@Override
		public void messageReceived(int arg0, Message arg1) {
			DataBytes = arg1.dataGet();
			synchronized (impl) {
				impl.notify();
			}
		}
	}

	@Override
	public void writeData(IData data, OutputStream oStream) {
		// TODO Auto-generated method stub
		
	}
}
