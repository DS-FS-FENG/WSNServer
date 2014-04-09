package framework.utilities;

import java.util.LinkedList;
import java.util.Queue;

import framework.eventcontroller.IController;

public class ControllingQueue {
	private final static int THREAD_SLEEP_MILLISECOND = 1000;

	private Queue<IController> controllerQueue;
	
	private ControllingQueue(){
		controllerQueue = new LinkedList<IController>();
		run();
	}
	
	private void run() {
		System.out.println("运行事务控制队列");
		Thread queueThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					if(!controllerQueue.isEmpty()){
						IController controller = controllerQueue.poll();
						controller.run();
					}
					else {
						try {
							Thread.sleep(THREAD_SLEEP_MILLISECOND);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		queueThread.start();
	}
	
	public static ControllingQueue getInstance(){
		return ControllingQueueHolder.Instance;
	}
	
	public void add(IController controller){
		controllerQueue.add(controller);
	}
	
	private static class ControllingQueueHolder {
		private static final ControllingQueue Instance = new ControllingQueue();
	}
}
