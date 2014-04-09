package cn.edu.buaa.sensory.message.response;

import cn.edu.buaa.sensory.message.ProtocolMessage;

public abstract class ReadResponse extends ProtocolMessage {
	protected byte dataNumber;
	protected Object[] datas;
	
	public ReadResponse() {
		
	}
	
	public ReadResponse(Object[] datas) {
		int unitlength = getUnitLength();
		if(datas.length % unitlength != 0 || datas.length / unitlength > 0xff)
			throw new IllegalArgumentException("输入的数据长度不符合要求");
		
		dataNumber = (byte)(datas.length / unitlength);
		this.datas = datas;
	}
	
	public byte getDataNumber() {
		return dataNumber;
	}
	
	public Object[] getDatas() {
		return datas;
	}
	
	protected abstract int getUnitLength();
}
