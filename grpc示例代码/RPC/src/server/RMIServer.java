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
			
			String name = "BookManagement";//���Ⱪ¶Զ�̶��������
            BookManage engine = new Booklmpl();
            //���ɰ���engine������������󣬼�skeleton����
            LocateRegistry.createRegistry(6089);
            BookManage skeleton = (BookManage) UnicastRemoteObject.exportObject(engine, 0);
            //��ȡע�����ĵ����ã�ʾ�����������ڱ��ؼ����
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
