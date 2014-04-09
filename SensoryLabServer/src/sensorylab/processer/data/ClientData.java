package sensorylab.processer.data;

import java.sql.ResultSet;

import cn.edu.buaa.sensory.message.IProtocolMessage;
import framework.processer.data.IData;

public class ClientData implements IData {
	private IProtocolMessage msg;
	
	public ClientData(IProtocolMessage msg) {
		this.msg = msg;
	}
	
	public byte getRequestType() {
		return msg.getDataType();
	}
	
	public IProtocolMessage getProtocolMessage() {
		return msg;
	}

	@Override
	public void Initialize(ResultSet rs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Initialize(byte[] bytes) {
		// TODO Auto-generated method stub

	}

}
