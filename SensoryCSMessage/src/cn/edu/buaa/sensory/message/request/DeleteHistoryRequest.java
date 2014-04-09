package cn.edu.buaa.sensory.message.request;

import java.util.Calendar;

import cn.edu.buaa.sensory.message.ProtocolMessage;

public abstract class DeleteHistoryRequest extends ProtocolMessage {
	protected String authenticationString;
	protected Calendar start;
	protected Calendar end;
	
	public DeleteHistoryRequest() {
		
	}
	
	public DeleteHistoryRequest(String authenticationString, Calendar start, Calendar end) {
		if(start.after(end))
			throw new IllegalArgumentException("起始时间不能晚于结束时间");
		
		this.authenticationString = authenticationString;
		this.start = start;
		this.end = end;
	}
	
	public String getAuthenticationString() {
		return authenticationString;
	}
	
	public Calendar getStart() {
		return start;
	}
	
	public Calendar getEnd() {
		return end;
	}
}
