package sensorylab.eventcontroller;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import framework.eventcontroller.IClientRequest;
import framework.eventcontroller.ISocketAnalyser;
import utilities.Utilities;

public class SensorySocketAnalyser implements ISocketAnalyser {
	
	public SensorySocketAnalyser() {
		System.out.println("生成Socket分析器");
	}
	
	@Override
	public IClientRequest analyse(Socket acceptedSocket) {
		try {
			InputStream iStream = new BufferedInputStream(acceptedSocket.getInputStream());
			OutputStream oStream = acceptedSocket.getOutputStream();
			iStream.mark(2);
			int requestType;
			String requestSource;
			int idCode = iStream.read();
			System.out.println("分析请求");
			switch (idCode) {
			case Utilities.WSNNetgateRequest:
				requestType = Utilities.WSNNetgateRequest;
				requestSource = Utilities.NetGate;
				break;
			case Utilities.ClientRequest:
				requestType = Utilities.ClientRequest;
				requestSource = Utilities.Client;
				break;
			default:
				requestType = Utilities.Exception;
				requestSource = Utilities.Unknown;
				break;
			}
			iStream.reset();
			ClientRequest request = new ClientRequest(requestType, requestSource);
			request.setInputStream(iStream);
			request.setOutputStream(oStream);
			return request;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
