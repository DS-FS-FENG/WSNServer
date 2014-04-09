package framework.eventcontroller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

import utilities.Utilities;
import framework.processer.event.IEvent;
import framework.server.IServer;

public abstract class Controller implements IController {
	protected IServer server;
	protected IEvent event;
	
	public Controller(IServer server, IEvent event) {
		this.server = server;
		this.event = event;
	}
	
	protected abstract ExecutorService getExecutor();
	protected abstract Connection getSqlConn() throws SQLException;
	protected abstract void handleException(Exception e);
	
	public void run(){
		System.out.println("处理事务");
		ExecutorService threadExecutor = getExecutor();
		threadExecutor.submit(new Runnable() {
			
			@Override
			public void run() {
				Connection sqlConn = null;
				try{
					sqlConn = getSqlConn();
					event.run(sqlConn);
					server.print(formatInformation("Success", event));
				}catch(Exception e){
					handleException(e);
				}finally {
					event.close();
					try {
						if(sqlConn != null)
							sqlConn.close();
					} catch (SQLException se) {
						se.printStackTrace();
					}
				}
			}
			
		});
	}
	
	protected String formatInformation(String s, IEvent event) {
		StringBuffer buffer = new StringBuffer(Utilities.getDataTimeNow() + "\n");
		buffer.append("Request from: " + event.getSource() + "\n");
		buffer.append("Status: " + s + "\n");
		return buffer.toString();
	}
}
