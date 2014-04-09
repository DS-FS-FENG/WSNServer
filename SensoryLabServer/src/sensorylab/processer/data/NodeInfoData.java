package sensorylab.processer.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import utilities.Utilities;

public class NodeInfoData extends Data {
	private boolean nodeIdUnique;
	
	private int nodeid;
	private Calendar datetime;
	private GPSData gpsData;
	private LocationData locationData;
	
	public NodeInfoData() {
		nodeIdUnique = false;
		gpsData = new GPSData();
		locationData = new LocationData();
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
		double longitude = rs.getDouble(index++);
		boolean isEastern = rs.getBoolean(index++);
		double latitude = rs.getDouble(index++);
		boolean isNorthern = rs.getBoolean(index++);
		double altitude = rs.getDouble(index++);
		double speed = rs.getDouble(index++);
		gpsData = new GPSData(longitude, isEastern, latitude, isNorthern, altitude, speed);
		longitude = rs.getDouble(index++);
		isEastern = rs.getBoolean(index++);
		latitude = rs.getDouble(index++);
		isNorthern = rs.getBoolean(index++);
		locationData = new LocationData(longitude, isEastern, latitude, isNorthern);
	}

	@Override
	public void Initialize(byte[] bytes) {
		// TODO Auto-generated method stub

	}
	
	public int getNodeid() {
		return nodeid;
	}
	
	public Calendar getDatetime() {
		return datetime;
	}
	
	public GPSData getGPSData() {
		return gpsData;
	}
	
	public LocationData getLocationData() {
		return locationData;
	}
	
	public void setNodeIdUnique(boolean nodeIdUnique) {
		this.nodeIdUnique = nodeIdUnique;
	}

	@Override
	public Object[] format() {
		List<Object> objects = new Vector<>();
		
		if(!nodeIdUnique)
			objects.add(nodeid);
		objects.add(datetime);
		objects.add(gpsData.getLongitude());
		objects.add(gpsData.getIsEastern());
		objects.add(gpsData.getLatitude());
		objects.add(gpsData.getIsNorthern());
		objects.add(gpsData.getAltitude());
		objects.add(gpsData.getSpeed());
		objects.add(locationData.getLongitude());
		objects.add(locationData.getIsEastern());
		objects.add(locationData.getLatitude());
		objects.add(locationData.getIsNorthern());
		
		return objects.toArray();
	}

}
