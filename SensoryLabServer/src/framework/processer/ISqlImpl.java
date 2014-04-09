package framework.processer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public interface ISqlImpl {
	//执行查询
	public ResultSet executeQuery(Connection connection, String command, Object[] values) throws SQLException;
	
	//执行其他
	public void execute(Connection connection, String command, Object[] values) throws SQLException;
}
