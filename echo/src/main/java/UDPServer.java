

import java.net.*;


public class UDPServer {
	public static void main(String[] args) {

		DatagramSocket aSocket = null;
		int serverPort = 6789;
		try{
			aSocket = new DatagramSocket(serverPort);

			System.out.println("Server is started, Waiting for new data.");
			while(true){
				// 创建线程循环
				UDPThread t1 = new UDPThread(aSocket);
				t1.run();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
