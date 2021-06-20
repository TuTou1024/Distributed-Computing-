

import java.net.*;


public class UDPThread extends Thread{
	DatagramSocket asocket = null;
	public UDPThread(DatagramSocket socket) {
		this.asocket = socket;
	}
	@Override
	public void run() {
		try {
			byte[] buffer=new byte[1000];
			int num = 0;
			while(true) {
			DatagramPacket request = new DatagramPacket(buffer, buffer.length);
			asocket.receive(request);
			
			byte[] data = request.getData();
			String message = new String(data,0,request.getLength());
			System.out.println("This is Thread "+num);
			num++;
			System.out.println("Message received from Client said:"+message);

			
			String data2="Server has received your message.";
			DatagramPacket reply = new DatagramPacket(data2.getBytes(),
			data2.getBytes().length, request.getAddress(), request.getPort());
			asocket.send(reply);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
