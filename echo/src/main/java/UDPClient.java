

import java.net.*;
import java.lang.management.ManagementFactory; 
import java.io.*;
import java.util.*;

public class UDPClient {
	public static void main(String[] args) {
		// args give message contents and server hostname
	    DatagramSocket aSocket = null;
		try {
			System.out.println("New data: ");

			aSocket = new DatagramSocket();
			InetAddress aHost = InetAddress.getByName("127.0.0.1");
			int serverPort = 6789;
			Scanner in = new Scanner(System.in);
			String data=in.nextLine();

			byte[] m = data.getBytes();

			System.out.println("Client send: "+data);

			DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
			aSocket.send(request);

			byte[] buffer = new byte[5000];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(reply);
			System.out.println("Reply: "+new String(reply.getData()));
			aSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            if(aSocket != null) aSocket.close();
        }

	}

}
