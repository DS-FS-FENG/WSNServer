package framework.utilities;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class SqlConnectionPool {
	private String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String url = "jdbc:sqlserver://localhost:1433;DatabaseName=SensoryLabDB";
	private String username = "SensoryLab";
	private String password = "sensory";
	private int initialSize = 10;
	private int maxActive = 30;
	private int maxIdle = 8;
	private int minIdle = 0;
	private int maxWait = 3000;
	
	private BasicDataSource basicDataSource;
	
	private SqlConnectionPool() {
		initialize();
	}
	
	private void initialize() {
		basicDataSource = new BasicDataSource();
		basicDataSource.setUrl(url);
		basicDataSource.setDriverClassName(driverName);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);
		basicDataSource.setInitialSize(initialSize);
		basicDataSource.setMaxActive(maxActive);
		basicDataSource.setMaxIdle(maxIdle);
		basicDataSource.setMinIdle(minIdle);
		basicDataSource.setMaxWait(maxWait);
	}
	
	//单例模式，获得唯一的连接池实例
	public static SqlConnectionPool getInstance() {
		return ConnectionPoolHolder.Instance;
	}
	
	public Connection getConnection() throws SQLException {
		System.out.println("请求数据库连接");
		return basicDataSource.getConnection();
	}
	
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	
	public void setURL(String url) {
		this.url = url;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	
	public void restart() throws SQLException {
		if(!basicDataSource.isClosed())
			basicDataSource.close();
		
		initialize();
	}
	
	private static class ConnectionPoolHolder {
		private static final SqlConnectionPool Instance = new SqlConnectionPool();
	}
}
