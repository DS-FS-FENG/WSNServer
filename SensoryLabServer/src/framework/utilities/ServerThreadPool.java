package framework.utilities;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerThreadPool {
	private int corePoolSize = 20;
	private int maxPoolSize = 100;
	private long keepAliveTime = 30;
	private TimeUnit unit = TimeUnit.SECONDS;
	private int queueLength = 30;
	
	private ExecutorService executor;
	
	private ServerThreadPool() {
		executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 
				keepAliveTime, unit, new ArrayBlockingQueue<Runnable>(queueLength));
	}
	
	//单例模式，获得唯一的线程池实例
	public static ServerThreadPool getInstance() {
		return ThreadPoolHolder.Instance;
	}
	
	public ExecutorService getExecutor() {
		System.out.println("请求线程");
		return executor;
	}
	
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}
	
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}
	
	public void setKeepAliveTime(int keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}
	
	public void setTimeUnit(TimeUnit unit) {
		this.unit = unit;
	}
	
	public void setQueueLength(int queueLength) {
		this.queueLength = queueLength;
	}
	
	public void restart() {
		if(!executor.isShutdown())
			executor.shutdown();
			
		executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 
				keepAliveTime, unit, new ArrayBlockingQueue<Runnable>(queueLength));
	}
	
	private static class ThreadPoolHolder {
		private static final ServerThreadPool Instance = new ServerThreadPool();
	}
}
