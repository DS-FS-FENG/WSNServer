package sensorylab.server;

import framework.server.IMessagePrinter;

public class SimplePrinter implements IMessagePrinter {

	public SimplePrinter() {
		System.out.println("���ɼ�����Ϣ��ӡ��");
	}
	
	@Override
	public void print(String message) {
		System.out.println(message);
	}

}
