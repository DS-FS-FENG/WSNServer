package framework.processer.event;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;

import sensorylab.processer.SensoryException;

public interface IEvent {
	public void run(Connection sqlConn) throws SensoryException, SQLException, IOException;
	public String getSource();
	public void setSource(String source);
	public void setStream(InputStream inputStream, OutputStream oStream);
	public void close();
}
