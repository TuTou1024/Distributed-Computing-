package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import bookInterface.BookManage;

public class RMIServer {
	public static void main(String[] args) {
		try {
			
			String name = "BookManagement";//对外暴露远程对象的名字
            BookManage engine = new Booklmpl();
            //生成包裹engine对象的容器对象，即skeleton对象
            LocateRegistry.createRegistry(6089);
            BookManage skeleton = (BookManage) UnicastRemoteObject.exportObject(engine, 0);
            //获取注册中心的引用，示例中心运行在本地计算机
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 6089);
            System.out.println("Registering BookMangement Object");
            registry.rebind(name, skeleton);
            
			/*
            BookManage BM = new Booklmpl();
			LocateRegistry.createRegistry(6089);
			Naming.rebind("rmi://127.0.0.1:6089/BM", BM);
			*/
			System.out.println("RMIServer started");
		}catch(Exception e) {
			System.err.println("Exception:" + e);
			e.printStackTrace();
		}
	}	
}
