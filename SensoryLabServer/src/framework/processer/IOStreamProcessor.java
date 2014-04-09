package framework.processer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import framework.processer.data.IData;

public abstract class IOStreamProcessor {
	protected IMessageImpl messageImpl;
	
	public IOStreamProcessor(IMessageImpl messageImpl) {
		this.messageImpl = messageImpl;
	}
	
	public abstract IData analyseStream(InputStream iStream);
	public abstract IData buildPackage(IData data);
	public abstract void writeStream(IData data, OutputStream oStream) throws IOException;
}
