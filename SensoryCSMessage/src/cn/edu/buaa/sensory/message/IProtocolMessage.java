package cn.edu.buaa.sensory.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.buaa.sensory.messagevisitor.MessageVisitor;

public interface IProtocolMessage {
	//操作类型
	public final static byte ExceptionResponse = 0x00;
	public final static byte ReadLastInformation = 0x01;
	public final static byte ReadNodeHistoryData = 0x02;
	public final static byte ReadNodesHistoryError = 0x03;
	public final static byte ReadActivedNodes = 0x04;
	public final static byte DeleteNodeHistoryData = 0x05;
	public final static byte DeleteAllHistoryData = 0x06;
	public final static byte Authentication = 0x07;
	
	//包头
	public final static byte ProtocolHead = (byte)0xEE;
	
	//异常码
	public final static byte UnexpectSreamSource = 0x00;
	public final static byte UnsuppotedFunction = 0x01;
	public final static byte IOFailure = 0x02;
	public final static byte ParseFailue = 0x03;
	public final static byte NoData = 0x04;
	public final static byte UserExisted = 0x05;
	
	public byte getDataType();
	public void read(DataInputStream iStream) throws IOException, MessageException;
	public void write(MessageVisitor visitor, DataOutputStream oStream) throws IOException;
}
