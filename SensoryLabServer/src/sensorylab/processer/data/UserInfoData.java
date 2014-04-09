package sensorylab.processer.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import framework.processer.data.IData;

public class UserInfoData implements IData {
	public final static int Administrator = 0x00;
	public final static int Guest = 0x01;
	
	private int permission = Guest;
	private String userName;
	private String password;
	private String authenticationString;
	
	public UserInfoData(){
	}
	
	public UserInfoData(String userName, String password){
		this.userName = userName;
		this.password = password;
	}
	
	public UserInfoData(String userName, String password, int permission){
		this(userName, password);
		this.permission = permission;
	}

	@Override
	public void Initialize(ResultSet rs) throws SQLException {
		rs.last();
		if(rs.getRow() > 1)
			throw new IllegalArgumentException("More than one data here");
		
		int index = 2;
		userName = rs.getString(index++);
		password = rs.getString(index++);
		permission = rs.getInt(index);
	}

	@Override
	public void Initialize(byte[] bytes) {
		// TODO Auto-generated method stub

	}
	
	public int getPermission() {
		return permission;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getAuthenticationString() {
		return authenticationString;
	}
	
	public void setAuthenticationString(String authenticationString) {
		this.authenticationString = authenticationString;
	}
	
	void setPermission(int permission) {
		this.permission = permission;
	}

}
