package cn.edu.buaa.sensory.message;

public class MessageException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private byte exceptionCode;
	
	public MessageException(byte exceptionCode){
		this.exceptionCode = exceptionCode;
	}
	
	public MessageException(byte exceptionCode, String msg) {
		super(msg);
		this.exceptionCode = exceptionCode;
	}
	
	public byte getExceptionCode() {
		return exceptionCode;
	}
}
