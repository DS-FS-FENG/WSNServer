package cn.edu.buaa.sensory.messagevisitor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.edu.buaa.sensory.message.request.AuthenticationRequest;
import cn.edu.buaa.sensory.message.request.DeleteAllHistoryRequest;
import cn.edu.buaa.sensory.message.request.DeleteNodeHistoryRequest;
import cn.edu.buaa.sensory.message.request.ReadActivedNodesRequest;
import cn.edu.buaa.sensory.message.request.ReadLastInformationRequest;
import cn.edu.buaa.sensory.message.request.ReadNodeHistoryRequest;
import cn.edu.buaa.sensory.message.request.ReadNodesHistoryErrorRequest;
import cn.edu.buaa.sensory.message.response.AuthenticationResponse;
import cn.edu.buaa.sensory.message.response.DeleteHistoryResponse;
import cn.edu.buaa.sensory.message.response.ExceptionResponse;
import cn.edu.buaa.sensory.message.response.ReadActivedNodesResponse;
import cn.edu.buaa.sensory.message.response.ReadLastInformationResponse;
import cn.edu.buaa.sensory.message.response.ReadNodeHistoryResponse;
import cn.edu.buaa.sensory.message.response.ReadNodesHistoryErrorResponse;

class SensoryMessageWriter implements IMessageWriter {
	private final static DateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public void write(DataOutputStream oStream, ReadLastInformationRequest msg)
			throws IOException {
		writeDate(msg.getStart(), oStream);
		int[] nodeIds = msg.getNodeIds();
		writeIds(nodeIds, oStream);
	}

	@Override
	public void write(DataOutputStream oStream, ReadNodeHistoryRequest msg)
			throws IOException {
		oStream.writeInt(msg.getNodeId());
		writeDate(msg.getStart(), oStream);
		writeDate(msg.getEnd(), oStream);
	}

	@Override
	public void write(DataOutputStream oStream, ReadNodesHistoryErrorRequest msg)
			throws IOException {
		int[] nodeIds = msg.getNodeIds();
		writeIds(nodeIds, oStream);
		writeDate(msg.getStart(), oStream);
		writeDate(msg.getEnd(), oStream);
	}

	@Override
	public void write(DataOutputStream oStream, ReadActivedNodesRequest msg)
			throws IOException {
		writeDate(msg.getStart(), oStream);
	}

	@Override
	public void write(DataOutputStream oStream, DeleteNodeHistoryRequest msg)
			throws IOException {
		oStream.writeUTF(msg.getAuthenticationString());
		oStream.writeInt(msg.getNodeId());
		writeDate(msg.getStart(), oStream);
		writeDate(msg.getEnd(), oStream);
	}

	@Override
	public void write(DataOutputStream oStream, DeleteAllHistoryRequest msg)
			throws IOException {
		oStream.writeUTF(msg.getAuthenticationString());
		writeDate(msg.getStart(), oStream);
		writeDate(msg.getEnd(), oStream);
	}

	@Override
	public void write(DataOutputStream oStream, AuthenticationRequest msg)
			throws IOException {
		oStream.writeByte(msg.getOperationType());
		oStream.writeUTF(msg.getUsername());
		oStream.writeUTF(msg.getPassword());
	}

	@Override
	public void write(DataOutputStream oStream, ExceptionResponse msg)
			throws IOException {
		oStream.writeByte(msg.getExceptionCode());
	}

	@Override
	public void write(DataOutputStream oStream, ReadLastInformationResponse msg)
			throws IOException {
		byte dataNum = msg.getDataNumber();
		Object[] datas = msg.getDatas();
		oStream.writeByte(dataNum);
		for(int i = 0; i < dataNum; i++) {
			int index = i * msg.UnitLength;
			oStream.writeInt((int)datas[index++]);
			writeDate((Calendar)datas[index++], oStream);
			oStream.writeDouble((double)datas[index++]);
			oStream.writeBoolean((boolean)datas[index++]);
			oStream.writeDouble((double)datas[index++]);
			oStream.writeBoolean((boolean)datas[index++]);
			oStream.writeDouble((double)datas[index++]);
			oStream.writeDouble((double)datas[index++]);
			oStream.writeDouble((double)datas[index++]);
			oStream.writeBoolean((boolean)datas[index++]);
			oStream.writeDouble((double)datas[index++]);
			oStream.writeBoolean((boolean)datas[index]);
		}
	}

	@Override
	public void write(DataOutputStream oStream, ReadNodeHistoryResponse msg)
			throws IOException {
		oStream.writeInt(msg.getNodeId());
		byte dataNum = msg.getDataNumber();
		Object[] datas = msg.getDatas();
		oStream.writeByte(dataNum);
		for(int i = 0; i < dataNum; i++) {
			int index = i * msg.UnitLength;
			writeDate((Calendar)datas[index++], oStream);
			oStream.writeDouble((double)datas[index++]);
			oStream.writeBoolean((boolean)datas[index++]);
			oStream.writeDouble((double)datas[index++]);
			oStream.writeBoolean((boolean)datas[index++]);
			oStream.writeDouble((double)datas[index++]);
			oStream.writeDouble((double)datas[index++]);
			oStream.writeDouble((double)datas[index++]);
			oStream.writeBoolean((boolean)datas[index++]);
			oStream.writeDouble((double)datas[index++]);
			oStream.writeBoolean((boolean)datas[index]);
		}
	}

	@Override
	public void write(DataOutputStream oStream, ReadNodesHistoryErrorResponse msg)
			throws IOException {
		byte dataNum = msg.getDataNumber();
		Object[] datas = msg.getDatas();
		oStream.writeByte(dataNum);
		for(int i = 0; i < dataNum; i++) {
			int index = i * msg.UnitLength;
			oStream.writeInt((int)datas[index++]);
			writeDate((Calendar)datas[index++], oStream);
			oStream.writeDouble((double)datas[index++]);
			oStream.writeDouble((double)datas[index]);
		}
	}

	@Override
	public void write(DataOutputStream oStream, ReadActivedNodesResponse msg)
			throws IOException {
		byte dataNum = msg.getDataNumber();
		Object[] datas = msg.getDatas();
		oStream.writeByte(dataNum);
		for(int i = 0; i < dataNum; i++) {
			int index = i * msg.UnitLength;
			oStream.writeInt((int)datas[index++]);
			writeDate((Calendar)datas[index++], oStream);
			oStream.writeInt((int)datas[index]);
		}
	}

	@Override
	public void write(DataOutputStream oStream, DeleteHistoryResponse msg)
			throws IOException {
		oStream.writeBoolean(msg.getIsSucceeded());
	}

	@Override
	public void write(DataOutputStream oStream, AuthenticationResponse msg)
			throws IOException {
		oStream.writeByte(msg.getPermission());
		oStream.writeUTF(msg.getAuthenticationString());
	}
	
	private void writeIds(int[] nodeIds, DataOutputStream oStream) throws IOException {
		oStream.writeByte(nodeIds.length);
		for (int i : nodeIds)
			oStream.writeInt(i);
	}
	
	private void writeDate(Calendar c, DataOutputStream oStream) throws IOException {
		oStream.writeUTF(DateFormat.format(c.getTime()));
	}

}
