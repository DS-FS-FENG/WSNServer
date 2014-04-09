package sensorylab.processer.data;

import java.sql.ResultSet;

import utilities.Utilities;
import framework.processer.data.IData;

public class SensoryTinyOSData implements IData {
	public final static int BytesLength = 2 +
			BasicInfoData.BytesLength + TempHumData.BytesLength + LocationData.BytesLength + GPSData.BytesLength;
	
	private BasicInfoData basicInfoData;
	private TempHumData tempHumData;
	private LocationData locationData;
	private GPSData gpsData;
	
	public SensoryTinyOSData() {
		basicInfoData = new BasicInfoData();
		tempHumData = new TempHumData();
		locationData = new LocationData();
		gpsData = new GPSData();
	}

	@Override
	public void Initialize(ResultSet rs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void Initialize(byte[] bytes) {
		if(bytes.length < BytesLength)
			throw new IllegalArgumentException("bytes is too short");
		
		byte[] nodeInfoBytes = Utilities.subBytes(bytes, 0, 5);
		byte[] tempHumDataBytes = Utilities.subBytes(bytes, 7, TempHumData.BytesLength);
		
		int index = 7 + TempHumData.BytesLength;
		byte[] locationDataBytes = Utilities.subBytes(bytes, index, LocationData.BytesLength);
		index += LocationData.BytesLength;
		byte[] dateTimeBytes = Utilities.subBytes(bytes, index, 7);
		index += 7;
		byte[] gpsDataBytes = Utilities.subBytes(bytes, index, GPSData.BytesLength);
		basicInfoData.Initialize(Utilities.contact(nodeInfoBytes, dateTimeBytes));
		tempHumData.Initialize(tempHumDataBytes);
		locationData.Initialize(locationDataBytes);
		gpsData.Initialize(gpsDataBytes);
	}
	
	public BasicInfoData getBasicInfoData(){
		return basicInfoData;
	}
	
	public TempHumData getTempHumData() {
		return tempHumData;
	}
	
	public LocationData getLocationData() {
		return locationData;
	}
	
	public GPSData getGpsData() {
		return gpsData;
	}

}
