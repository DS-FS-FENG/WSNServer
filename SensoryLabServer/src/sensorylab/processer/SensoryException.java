package sensorylab.processer;

public class SensoryException extends Exception {
	private final static long serialVersionUID = 1L;
	
	public final static int NoDataException = 0;
	public final static int StreamParseException = 1;
	public final static int UnknownSourceException = 2;
	
	private int exceptionType;
	
	public SensoryException(String message, int exceptionType) {
		super(message);
		this.exceptionType = exceptionType;
	}
	
	public int getExceptionType() {
		return exceptionType;
	}
}
