package sensorylab.processer.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

import utilities.Utilities;

public class BasicInfoData extends Data {
	public final static int BytesLength = 12;
	
	private int nodeID;
	private int parentNode;
	private int hop;
	private Calendar datetime;
	
	public BasicInfoData() {
		
	}
	
	public BasicInfoData(int nodeID) {
		this.nodeID = nodeID;
	}
	
	public BasicInfoData(int nodeID, int parentNode, int hop, Calendar datetime) {
		this(nodeID);
		this.parentNode = parentNode;
		this.hop = hop;
		this.datetime = datetime;
	}

	@Override
	public void Initialize(ResultSet rs) throws SQLException {
		int startIndex = 2;
		nodeID = rs.getInt(startIndex++);
		parentNode = rs.getInt(startIndex++);
		hop = rs.getInt(startIndex++);
		datetime = Calendar.getInstance();
		try {
			datetime.setTime(Utilities.DatetimeFormat.parse(rs.getString(startIndex)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void Initialize(byte[] bytes) {
		if(bytes.length < BytesLength)
			throw new IllegalArgumentException("bytes is too short");
		
		nodeID = Utilities.combineBytes(Utilities.subBytes(bytes, 0, 2));
		parentNode = Utilities.combineBytes(Utilities.subBytes(bytes, 2, 2));
		hop = bytes[4];
		datetime = Calendar.getInstance();
		int year = Utilities.combineBytes(Utilities.subBytes(bytes, 5, 2));
		datetime.set(year, bytes[7] - 1, bytes[8], bytes[9], bytes[10], bytes[11]);
	}
	
	public int getNodeID() {
		return nodeID;
	}
	
	public int getParentNode() {
		return parentNode;
	}
	
	public int getHop() {
		return hop;
	}
	
	public Calendar getDatetime() {
		return datetime;
	}

	@Override
	public Object[] format() {
		return new Object[] { nodeID, datetime, parentNode };
	}
}
