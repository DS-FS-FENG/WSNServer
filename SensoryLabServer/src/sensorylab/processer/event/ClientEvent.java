package sensorylab.processer.event;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import sensorylab.processer.SensoryDataBaseProcessor;
import sensorylab.processer.data.SensoryExceptionData;
import framework.processer.IOStreamProcessor;
import framework.processer.data.IData;
import framework.processer.event.IEvent;

public class ClientEvent implements IEvent {
	private String source;
	private SensoryDataBaseProcessor dbProcessor;
	private IOStreamProcessor ioProcessor;
	private InputStream iStream;
	private OutputStream oStream;
	
	public ClientEvent(IOStreamProcessor ioProcessor, SensoryDataBaseProcessor dbProcessor) {
		this.ioProcessor = ioProcessor;
		this.dbProcessor = dbProcessor;
	}
	
	@Override
	public void run(Connection sqlConn) throws SQLException, IOException {
		System.out.println("Ö´ÐÐÊÂ¼þ from " + getSource());
		IData request = ioProcessor.analyseStream(iStream);
		IData response;
		
		if(request.getClass().equals(SensoryExceptionData.class))
			response = ((SensoryExceptionData)request).createExceptionResponse();
		else 
			response = RequestHandler.handle(request, sqlConn, dbProcessor);
			
		ioProcessor.writeStream(response, oStream);
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
	
	@Override
	public void close() {
		try {
			iStream.close();
			oStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
