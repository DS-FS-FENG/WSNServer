package sensorylab.eventcontroller;

import java.io.InputStream;
import java.io.OutputStream;

import framework.eventcontroller.IClientRequest;

public class ClientRequest implements IClientRequest {
	private int requestType;
	private String requestSource;
	private InputStream inputStream;
	private OutputStream outputStream;
	
	public ClientRequest(int requestType, String requestSource) {
		this.requestType = requestType;
		this.requestSource = requestSource;
	}
	
	@Override
	public int getRequestType() {
		return requestType;
	}

	@Override
	public String getRequestSource() {
		return requestSource;
	}

	@Override
	public InputStream getInputStream() {
		return inputStream;
	}
	
	@Override
	public OutputStream getOutputStream() {
		return outputStream;
	}
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
}
