package cn.edu.buaa.sensory.messagevisitor;

import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.buaa.sensory.message.request.*;
import cn.edu.buaa.sensory.message.response.*;

public interface IMessageWriter {
	public void write(DataOutputStream oStream, ReadLastInformationRequest msg) throws IOException;
	public void write(DataOutputStream oStream, ReadNodeHistoryRequest msg) throws IOException;
	public void write(DataOutputStream oStream, ReadNodesHistoryErrorRequest msg) throws IOException;
	public void write(DataOutputStream oStream, ReadActivedNodesRequest msg) throws IOException;
	public void write(DataOutputStream oStream, DeleteNodeHistoryRequest msg) throws IOException;
	public void write(DataOutputStream oStream, DeleteAllHistoryRequest msg) throws IOException;
	public void write(DataOutputStream oStream, AuthenticationRequest msg) throws IOException;
	
	public void write(DataOutputStream oStream, ExceptionResponse msg) throws IOException;
	public void write(DataOutputStream oStream, ReadLastInformationResponse msg) throws IOException;
	public void write(DataOutputStream oStream, ReadNodeHistoryResponse msg) throws IOException;
	public void write(DataOutputStream oStream, ReadNodesHistoryErrorResponse msg) throws IOException;
	public void write(DataOutputStream oStream, ReadActivedNodesResponse msg) throws IOException;
	public void write(DataOutputStream oStream, DeleteHistoryResponse msg) throws IOException;
	public void write(DataOutputStream oStream, AuthenticationResponse msg) throws IOException;
}
