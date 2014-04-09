package framework.eventcontroller;

import java.io.InputStream;
import java.io.OutputStream;

public interface IClientRequest {
	public int getRequestType();
	public String getRequestSource();
	public InputStream getInputStream();
	public OutputStream getOutputStream();
}
