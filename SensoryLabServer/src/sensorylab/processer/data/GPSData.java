package sensorylab.processer.data;

import java.sql.ResultSet;

import utilities.Utilities;
import framework.processer.data.IData;

public class GPSData implements IData {
	public final static int BytesLength = 14;
	
	private double longitude;
	private boolean isEastern;
	private double latitude;
	private boolean isNorthern;
	private double altitude;
	private double speed;
	
	public GPSData() {
		
	}
	
	public GPSData(double longitude, boolean isEastern, double latitude, boolean isNorthern, double altitude, double speed) {
		this.longitude = longitude;
		this.isEastern = isEastern;
		this.latitude = latitude;
		this.isNorthern = isNorthern;
		this.altitude = altitude;
		this.speed = speed;
	}
	
	@Override
	public void Initialize(ResultSet rs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Initialize(byte[] bytes) {
		if(bytes.length < BytesLength)
			throw new IllegalArgumentException("bytes is too short");
		
		longitude = (double)(Utilities.combineBytes(Utilities.subBytes(bytes, 0, 4))) / 10000000;
		isEastern = bytes[4] != Utilities.WesternMark;
		latitude = (double)(Utilities.combineBytes(Utilities.subBytes(bytes, 5, 4))) / 10000000;
		isNorthern = bytes[9] != Utilities.SouthernMark;
		altitude = (double)(Utilities.combineBytes(Utilities.subBytes(bytes, 10, 2))) / 10;
		speed = (double)(Utilities.combineBytes(Utilities.subBytes(bytes, 12, 2))) / 1000;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public boolean getIsEastern() {
		return isEastern;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public boolean getIsNorthern() {
		return isNorthern;
	}
	
	public double getAltitude() {
		return altitude;
	}
	
	public double getSpeed() {
		return speed;
	}

}
