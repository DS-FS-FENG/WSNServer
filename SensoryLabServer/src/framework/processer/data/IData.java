package framework.processer.data;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IData {
	public void Initialize(ResultSet rs) throws SQLException;
	public void Initialize(byte[] bytes);
}
