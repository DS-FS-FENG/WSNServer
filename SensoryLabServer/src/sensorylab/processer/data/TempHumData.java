package sensorylab.processer.data;

import java.sql.ResultSet;

import utilities.Utilities;
import framework.processer.data.IData;

public class TempHumData implements IData {
	public final static int BytesLength = 6;
	
	private double illuminance;
	private double temperature;
	private double humidity;
	
	public TempHumData() {
		
	}
	
	public TempHumData(double illuminance, double temperature, double humidity) {
		this.illuminance = illuminance;
		this.temperature = temperature;
		this.humidity = humidity;
	}

	@Override
	public void Initialize(ResultSet rs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void Initialize(byte[] bytes) {
		if(bytes.length < BytesLength)
			throw new IllegalArgumentException("bytes is too short");
		
		illuminance = (double)(Utilities.combineBytes(Utilities.subBytes(bytes, 0, 2))) / 100;
		temperature = (double)(Utilities.combineBytes(Utilities.subBytes(bytes, 2, 2))) / 100;
		humidity = (double)(Utilities.combineBytes(Utilities.subBytes(bytes, 4, 2))) / 100;
	}
	
	public double getIlluminance() {
		return illuminance;
	}
	
	public double getTemperature() {
		return temperature;
	}
	
	public double getHumidity() {
		return humidity;
	}
}
