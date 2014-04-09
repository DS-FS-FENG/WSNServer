package sensorylab.processer.event;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import sensorylab.processer.SensoryException;
import sensorylab.processer.data.SensoryTinyOSData;
import sensorylab.processer.SensoryDataBaseProcessor;
import framework.processer.IOStreamProcessor;
import framework.processer.event.IEvent;

public class GatewayEvent implements IEvent {
	private String source;
	private IOStreamProcessor ioProcessor;
	private SensoryDataBaseProcessor dbProcessor;
	private InputStream iStream;
	private OutputStream oStream;
	
	public GatewayEvent(IOStreamProcessor ioProcessor, SensoryDataBaseProcessor dbProcessor) {
		this.ioProcessor = ioProcessor;
		this.dbProcessor = dbProcessor;
	}

	@Override
	public void run(Connection sqlConn) throws SensoryException, SQLException, IOException {
		System.out.println("Ö´ÐÐÊÂ¼þ from " + getSource());
		SensoryTinyOSData data = (SensoryTinyOSData)ioProcessor.analyseStream(iStream);
		if(data == null)
			throw new SensoryException("failed to parse the input stream", SensoryException.StreamParseException);
		
		dbProcessor.writeData(data, sqlConn);
	}

	@Override
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public void setStream(InputStream iStream, OutputStream oStream) {
		this.iStream = iStream;
		this.oStream = oStream;
	}
	
	public void close() {
		try {
			iStream.close();
			oStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
