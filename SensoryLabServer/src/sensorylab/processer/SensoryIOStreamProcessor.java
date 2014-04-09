package sensorylab.processer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import framework.processer.IMessageImpl;
import framework.processer.IOStreamProcessor;
import framework.processer.data.IData;

public class SensoryIOStreamProcessor extends IOStreamProcessor {
	
	public SensoryIOStreamProcessor(IMessageImpl impl) {
		super(impl);
		System.out.println("�������������������");
	}
	
	@Override
	public IData analyseStream(InputStream iStream) {
		System.out.println("������");
		return messageImpl.unpackage(iStream);
	}

	@Override
	public IData buildPackage(IData data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeStream(IData data, OutputStream oStream) throws IOException {
		System.out.println("д����");
		messageImpl.writeData(data, oStream);
	}

}
