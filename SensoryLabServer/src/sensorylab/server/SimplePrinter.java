package sensorylab.server;

import framework.server.IMessagePrinter;

public class SimplePrinter implements IMessagePrinter {

	public SimplePrinter() {
		System.out.println("生成简易消息打印器");
	}
	
	@Override
	public void print(String message) {
		System.out.println(message);
	}

}
