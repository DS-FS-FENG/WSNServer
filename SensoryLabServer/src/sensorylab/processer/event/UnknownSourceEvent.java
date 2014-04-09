package sensorylab.processer.event;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import sensorylab.processer.SensoryException;
import utilities.Utilities;
import framework.processer.event.IEvent;

public class UnknownSourceEvent implements IEvent {
	private InputStream iStream;
	private OutputStream oStream;
	
	@Override
	public void run(Connection sqlConn) throws SensoryException, SQLException {
		System.out.println("Ö´ÐÐÊÂ¼þ from " + getSource());
		throw new SensoryException("unknown source", SensoryException.UnknownSourceException);
	}

	@Override
	public String getSource() {
		return Utilities.Unknown;
	}

	@Override
	public void setSource(String source) {
		throw new UnsupportedOperationException();
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
