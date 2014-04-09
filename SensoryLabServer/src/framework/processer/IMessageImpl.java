package framework.processer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import framework.processer.data.IData;

public interface IMessageImpl {
	public byte[] readBytes(InputStream iStream);
	public IData unpackage(InputStream iStream);
	public IData unpackage(byte[] packageBytes);
	public void writeData(IData data, OutputStream oStream) throws IOException;
}
