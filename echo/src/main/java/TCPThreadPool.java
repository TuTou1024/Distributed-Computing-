

import java.io.*;
import java.net.*;

public class TCPThreadPool extends Thread {
	
	Socket socket = null;
	
	public TCPThreadPool(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		InputStream inputStream = null;
		InputStreamReader inStreamReader = null;
		BufferedReader bufferedReader = null;
		OutputStream outStream = null;
		PrintWriter printWriter = null;
		try {
			inputStream = socket.getInputStream();
			inStreamReader = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(inStreamReader);
			outStream = socket.getOutputStream();
			printWriter = new PrintWriter(outStream);

			String line = null;
			int num = 0;
			while((line = bufferedReader.readLine())!=null) {
				System.out.println("This is Thread "+num);
				System.out.println("Message from client: "+line);
				printWriter.println(line);
				printWriter.flush();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			// 关掉通道
			try {
				if(printWriter!=null)
					printWriter.close();
				if(outStream!=null)
					outStream.close();
				if(bufferedReader!=null)
					bufferedReader.close();
				if(inStreamReader!=null)
					inStreamReader.close();
				if(inputStream!=null)
					inputStream.close();
				if(socket!=null)
					socket.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
