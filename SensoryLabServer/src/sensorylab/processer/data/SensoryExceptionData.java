package sensorylab.processer.data;

import java.sql.ResultSet;

import cn.edu.buaa.sensory.message.response.ExceptionResponse;
import framework.processer.data.IData;

public class SensoryExceptionData implements IData {
	private int exceptionCode;
	//private String message;
	
	public SensoryExceptionData(int exceptionCode) {
		this.exceptionCode = exceptionCode;
		//this.message = message;
	}
	
//	public String getMessage() {
//		return message;
//	}
	
	public int getExceptionCode() {
		return exceptionCode;
	}
	
	public IData createExceptionResponse() {
		return new ClientData(new ExceptionResponse((byte)exceptionCode));
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
