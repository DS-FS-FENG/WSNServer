package framework.processer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public interface ISqlImpl {
	//ִ�в�ѯ
	public ResultSet executeQuery(Connection connection, String command, Object[] values) throws SQLException;
	
	//ִ������
	public void execute(Connection connection, String command, Object[] values) throws SQLException;
}
