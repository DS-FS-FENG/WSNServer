package sensorylab.eventcontroller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

import framework.eventcontroller.Controller;
import framework.processer.event.IEvent;
import framework.server.IServer;
import framework.utilities.ServerThreadPool;
import framework.utilities.SqlConnectionPool;

public class SenseryLabController extends Controller {

	public SenseryLabController(IServer server, IEvent event) {
		super(server, event);
	}

	@Override
	protected ExecutorService getExecutor() {
		return ServerThreadPool.getInstance().getExecutor();
	}

	@Override
	protected Connection getSqlConn() throws SQLException {
		return SqlConnectionPool.getInstance().getConnection();
	}

	@Override
	protected void handleException(Exception e) {
		System.out.println(e.getMessage());
	}
	
}
