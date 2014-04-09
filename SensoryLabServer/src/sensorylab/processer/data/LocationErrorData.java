package sensorylab.processer.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

import utilities.Utilities;

public class LocationErrorData extends Data {
	private int nodeid;
	private Calendar datetime;
	private double longitudeError;
	private double latitudeError;
	
	public LocationErrorData() {
		
	}
	
	@Override
	public void Initialize(ResultSet rs) throws SQLException {
		int index = 1;
		nodeid = rs.getInt(index++);
		datetime = Calendar.getInstance();
		try {
			datetime.setTime(Utilities.DatetimeFormat.parse(rs.getString(index++)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		longitudeError = rs.getDouble(index++);
		latitudeError = rs.getDouble(index);
	}

	@Override
	public void Initialize(byte[] bytes) {
		// TODO Auto-generated method stub

	}
	
	public int getNodeID() {
		return nodeid;
	}
	
	public Calendar getDatetime() {
		return datetime;
	}
	
	public double getLongitudeError() {
		return longitudeError;
	}
	
	public double getLatitudeError() {
		return latitudeError;
	}

	@Override
	public Object[] format() {
		return new Object[] { nodeid, datetime, longitudeError, latitudeError};
	}

}
