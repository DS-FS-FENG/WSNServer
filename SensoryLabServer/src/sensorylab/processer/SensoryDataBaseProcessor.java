package sensorylab.processer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import sensorylab.processer.data.BasicInfoData;
import sensorylab.processer.data.Data;
import sensorylab.processer.data.LocationErrorData;
import sensorylab.processer.data.NodeInfoData;
import sensorylab.processer.data.SensoryTinyOSData;
import sensorylab.processer.data.UserInfoData;
import utilities.Utilities;
import framework.processer.DataBaseProcessor;
import framework.processer.ISqlImpl;
import framework.processer.data.IData;

public class SensoryDataBaseProcessor extends DataBaseProcessor {
	//table name
	protected final static String UserInfoTableName = "user_info";
	protected final static String BasicInfoTableName = "basic_info";
	protected final static String GPSDataTableName = "gps_data";
	protected final static String LocationDataTableName = "location_data";
	
	//view name
	protected final static String DataViewName = "dataview";
	protected final static String ErrorViewName = "errorview";
	
	//element name
	protected final static String UserName = "userName";
	protected final static String Password = "password";
	protected final static String Permission = "permission";
	protected final static String DateTime = "datetime";
	protected final static String NodeID = "nodeid";
	protected final static String LongtitudeError = "longtitude_error";
	protected final static String LatitudeError = "latitude_error";
	
	public static Map<String, String> AuthorizedUser = new HashMap<>(); 
	
	public SensoryDataBaseProcessor(ISqlImpl sqlImpl) {
		super(sqlImpl);
	}
	
	public IData readUserInfo(String username, String password, Connection connection) throws SQLException, SensoryException {
		System.out.println("读取用户信息");
		
		String command = "select * from " + UserInfoTableName + " where " + UserName + "=? and" + Password + " =?";
		Object[] values = new Object[] {username, password};
		ResultSet rs = sqlImpl.executeQuery(connection, command, values);
		
		if(rs.wasNull())
			throw new SensoryException("找不到用户或者密码错误", SensoryException.NoDataException);
		
		UserInfoData userData = new UserInfoData();
		userData.Initialize(rs);
		return userData;
	}

	public void addUserInfo(IData data, Connection connection) throws SQLException {
		System.out.println("添加用户信息");
		if(!data.getClass().equals(UserInfoData.class))
			throw new IllegalArgumentException("the type of data must be UserInfoData");
		
		UserInfoData uData = (UserInfoData)data;
		String username = uData.getUserName();
		String password = uData.getPassword();
		int permission = UserInfoData.Guest;
		Object[] values = new Object[] { username, password, permission };
		String command = "insert into " + UserInfoTableName + getInsertString(values.length);
		sqlImpl.execute(connection, command, values);
	}
	
	private String getInsertString(int valueNumber) {
		String insertString = " values(?";
		for(int i = 0; i < valueNumber - 1; i++)
			insertString += ",?";
		insertString += ")";
		return insertString;
	}
	
	public IData[] readBasicInfo(Calendar start, Connection connection) throws SQLException, SensoryException {
		System.out.println("读取基本信息（起始时间）");
		
		String subCommand = "(select " + NodeID + ", MAX(" + DateTime + ") as lasttime from " + BasicInfoTableName
				+ " where " + DateTime + " >= ? group by nodeid) lastResult";
		String command = "select bi.* from " + BasicInfoTableName + " bi, " + subCommand + " where bi." + NodeID +
				" = lastResult." + NodeID + " and bi." + DateTime + " = lastResult.lasttime";
		String datetime = Utilities.DatetimeFormat.format(start.getTime());
		Object[] values = new Object[] { datetime };
		ResultSet rs = sqlImpl.executeQuery(connection, command, values);
		
		if(rs.wasNull())
			throw new SensoryException("查无该时段数据", SensoryException.NoDataException);
		
		return resultSetToDatas(rs, BasicInfoData.class);
	}
	
	private <T extends Data> IData[] resultSetToDatas(ResultSet rs, Class<T> c) throws SQLException{
		List<IData> list = new Vector<>();
		try {
			while(rs.next()) {
				IData data = c.newInstance();
				data.Initialize(rs);
				list.add(data);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) { 
			e.printStackTrace();
		}
		
		IData[] result = new IData[list.size()];
		return list.toArray(result);
	}
	
	private IData[] resultSetToDatas(ResultSet rs) throws SQLException{
		List<IData> list = new Vector<>();
		while(rs.next()) {
			NodeInfoData data = new NodeInfoData();
			data.setNodeIdUnique(true);
			data.Initialize(rs);
			list.add(data);
		}
		
		IData[] result = new IData[list.size()];
		return list.toArray(result);
	}
	
	public IData[] readLastRecord(Calendar start, int[] nodeids, Connection connection) throws SQLException, SensoryException {
		System.out.println("读取最新记录");
		
		String subCommand = "(select " + NodeID + ", MAX(" + DateTime + ") as lasttime from " + BasicInfoTableName
				+ " where " + NodeID + " in("+ linkStrings("?", ",", nodeids.length) + ") and "
				+ DateTime + " >= ? group by nodeid) lastResult";
		String command = "select dv.* from " + DataViewName + " dv, " + subCommand + " where dv." + NodeID
				+ " = lastResult." + NodeID + " and " + "dv." + DateTime + " = lastResult.lasttime";
		
		Object[] values = new Object[nodeids.length + 1];
		Integer[] nodeidsInt = toIntegerArray(nodeids);
		System.arraycopy(nodeidsInt, 0, values, 0, nodeids.length);
		values[values.length - 1] = Utilities.DatetimeFormat.format(start.getTime());
		ResultSet rs = sqlImpl.executeQuery(connection, command, values);
		
		if(rs.wasNull())
			throw new SensoryException("查无该时段数据", SensoryException.NoDataException);
		
		return resultSetToDatas(rs, NodeInfoData.class);
	}
	
	public IData[] readNodeRecord(int nodeid, Calendar start, Calendar end, Connection connection) throws SQLException, SensoryException {
		System.out.println("读取历史记录");
		if(start.after(end))
			throw new IllegalArgumentException("起始时间应早于结束时间");
		
		String command = "select * from " + DataViewName + " dv where dv." + NodeID + " = ? and dv." + 
				DateTime + " between ? and ?";
		String startTime = Utilities.DatetimeFormat.format(start.getTime());
		String endTime = Utilities.DatetimeFormat.format(end.getTime());
		Object[] values = new Object[] { nodeid, startTime, endTime };
		ResultSet rs = sqlImpl.executeQuery(connection, command, values);
		
		if(rs.wasNull())
			throw new SensoryException("查无该时段数据", SensoryException.NoDataException);
		
		return resultSetToDatas(rs);
	}
	
	public IData[] readNodeError(Calendar start, Calendar end, int[] nodeids, Connection connection) throws SQLException, SensoryException {
		System.out.println("读取误差");
		if(start.after(end))
			throw new IllegalArgumentException("起始时间应早于结束时间");
		
		String command = "select * from " + ErrorViewName + " where " + NodeID + " in(" + 
				linkStrings("?", ",", nodeids.length) + ") and " + DateTime + " between ? and ?";
		Object[] values = new Object[nodeids.length + 2];
		Integer[] nodeidsInt = toIntegerArray(nodeids);
		System.arraycopy(nodeidsInt, 0, values, 0, nodeids.length);
		int index = nodeids.length;
		values[index++] = Utilities.DatetimeFormat.format(start.getTime());
		values[index] = Utilities.DatetimeFormat.format(end.getTime());
		ResultSet rs = sqlImpl.executeQuery(connection, command, values);
		
		if(rs.wasNull())
			throw new SensoryException("查无该时段数据", SensoryException.NoDataException);
		
		return resultSetToDatas(rs, LocationErrorData.class);
	}
	
	public void delete(int nodeid, Calendar start, Calendar end, Connection connection) throws SQLException {
		System.out.println("读取删除节点" + nodeid + "数据");
		if(start.after(end))
			throw new IllegalArgumentException("起始时间应早于结束时间");
		
		String command = "delete from " + BasicInfoTableName + " where " + NodeID + " = ? and " +
				DateTime + " between ? and ?";
		String startTime = Utilities.DatetimeFormat.format(start.getTime());
		String endTime = Utilities.DatetimeFormat.format(end.getTime());
		Object[] values = new Object[] { nodeid, startTime, endTime };
		sqlImpl.execute(connection, command, values);
	}
	
	public void delete(Calendar start, Calendar end, Connection connection) throws SQLException {
		System.out.println("删除数据");
		if(start.after(end))
			throw new IllegalArgumentException("起始时间应早于结束时间");
		
		String command = "delete from " + BasicInfoTableName + " where " + DateTime + " between ? and ?";
		String startTime = Utilities.DatetimeFormat.format(start.getTime());
		String endTime = Utilities.DatetimeFormat.format(end.getTime());
		Object[] values = new Object[] { startTime, endTime };
		sqlImpl.execute(connection, command, values);
	}
	
	private String linkStrings(String element, String separator, int length) {
		String eString = element;
		for(int i = 1; i < length; i++)
			eString += separator + element;
		return eString;
	}
	
	public void writeData(IData data, Connection connection) throws SQLException {
		System.out.println("更新数据库");
		if(!data.getClass().equals(SensoryTinyOSData.class))
			throw new IllegalArgumentException("the type of data must be SensoryTinyOSData");
		
		SensoryTinyOSData typedData = (SensoryTinyOSData)data;
		double g_Longitude = typedData.getGpsData().getLongitude();
		double g_Latitude = typedData.getGpsData().getLatitude();
		double l_Longitude = typedData.getLocationData().getLongitude();
		double l_Latitude = typedData.getLocationData().getLatitude();
		double longitudeErr = Math.abs(g_Longitude - l_Longitude);
		double latitudeErr = Math.abs(g_Latitude - l_Latitude);
		
		String command = "{call dbo.SensorDataInsert(" + linkStrings("?", ",", 19) + ")}";
		Object[] values = new Object[] {
				typedData.getBasicInfoData().getNodeID(),
				typedData.getBasicInfoData().getParentNode(),
				typedData.getBasicInfoData().getHop(),
				Utilities.DatetimeFormat.format(typedData.getBasicInfoData().getDatetime().getTime()),
				typedData.getTempHumData().getIlluminance(),
				typedData.getTempHumData().getTemperature(),
				typedData.getTempHumData().getHumidity(),
				l_Longitude,
				typedData.getLocationData().getIsEastern(),
				l_Latitude,
				typedData.getLocationData().getIsNorthern(),
				longitudeErr,
				latitudeErr,
				g_Longitude,
				typedData.getGpsData().getIsEastern(),
				g_Latitude,
				typedData.getGpsData().getIsNorthern(),
				typedData.getGpsData().getAltitude(),
				typedData.getGpsData().getSpeed()
		};
		sqlImpl.execute(connection, command, values);
	}
	
	private Integer[] toIntegerArray(int[] intArray) {
		int length = intArray.length;
		Integer[] integerArray = new Integer[length];
		for(int i = 0; i < length; i++)
			integerArray[i] = intArray[i];
		return integerArray;
	}
}
