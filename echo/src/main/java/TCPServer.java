

import java.io.*;
import java.net.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TCPServer {
	public static void main(String[] args) throws IOException {
		ServerSocket ListenSocket = new ServerSocket(8189);
		System.out.println("Server listening at 8189");
		ThreadPoolExecutor threadpool = new ThreadPoolExecutor(3,5,200,
				TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(3));
		System.out.println("Server started, waiting for client connection");
		while(true) {
			// 接受信息
			Socket clientSocket = ListenSocket.accept();
			System.out.println("Accepted connection from client");
			// 创建线程池对象并执行
			TCPThreadPool socket = new TCPThreadPool(clientSocket);
			threadpool.execute(socket);
		}
	}

}
