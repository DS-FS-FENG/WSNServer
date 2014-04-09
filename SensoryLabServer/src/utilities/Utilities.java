package utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

public final class Utilities {
	public final static int WSNNetgateRequest = 0x7E;
	public final static int ClientRequest = 0xEE;
	public final static int Exception = 0xFE;
	
	public final static String NetGate = "wsn gateway";
	public final static String Client = "client";
	public final static String Unknown = "unknown source";
	
	public final static byte WesternMark = 6;
	public final static byte SouthernMark = 7; 
	
	public final static DateFormat DatetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getDataTimeNow(){
		return DatetimeFormat.format(new java.util.Date());
	}
	
	public static Object[] contact(Object[] array1, Object[] array2) {
		if(!array1.getClass().equals(array2.getClass()))
			throw new IllegalArgumentException("the type of this two arrays must be the same");
		
		int array1Length = array1.length;
		int array2Length = array2.length;
		Object[] newArray = new Object[array1Length + array2Length];
		System.arraycopy(array1, 0, newArray, 0, array1Length);
		System.arraycopy(array2, 0, newArray, array1Length, array2Length);
		return newArray;
	}
	
	public static int combineBytes(byte[] bytes) {
		if(bytes.length > 4)
			throw new IllegalArgumentException("bytes is too long");
		int result = 0;
		for(int i = 0; i < bytes.length; i++)
			result = (result << 8) | (bytes[i] & 0xff);
		
		return result;
	}
	
	public static byte[] subBytes(byte[] bytes, int start, int length) {
		if(start + length > bytes.length)
			throw new ArrayIndexOutOfBoundsException();
		
		byte[] subBytes = new byte[length];
		System.arraycopy(bytes, start, subBytes, 0, length);
		return subBytes;
	}
	
	public static byte[] contact(byte[] bytes1, byte[] bytes2) {
		byte[] result = new byte[bytes1.length + bytes2.length];
		System.arraycopy(bytes1, 0, result, 0, bytes1.length);
		System.arraycopy(bytes2, 0, result, bytes1.length, bytes2.length);
		return result;
	}
	
	private final static String randomString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789%^$&*#+)"; 
	
	public static String createRamdomString(int length) {
		Random random = new Random();
		int maxNum = randomString.length();
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < length; i++) {
			int num = random.nextInt(maxNum);
			buffer.append(randomString.charAt(num));
		}
		
		return buffer.toString();
	}
}
