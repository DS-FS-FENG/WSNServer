package framework.processer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlImpl implements ISqlImpl {
	
	public SqlImpl() {
		System.out.println("生成数据库执行器");
	}
	
	private PreparedStatement getStatement(Connection connection, String preparedCommand, Object[] values) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(preparedCommand);
		for(int i = 0; i < values.length; i++)
			statement.setString(i + 1, values[i].toString());
		return statement;
	}

	@Override
	public ResultSet executeQuery(Connection connection, String command, Object[] values) throws SQLException {
		PreparedStatement statement = getStatement(connection, command, values);
		return statement.executeQuery();
	}

	@Override
	public void execute(Connection connection, String command, Object[] values) throws SQLException {
		PreparedStatement statement = getStatement(connection, command, values);
		statement.execute();
	}
}
