package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import BookInterface.Management;

public class RMIServer {
    public static void main(String[] args){
        try{
            //对外暴露远程对象的名字
            String name = "Management";
            Management engine = new BookFunction();
            //生成包裹engine对象的容器对象
            LocateRegistry.createRegistry(6089);
            Management skeleton =(Management) UnicastRemoteObject.exportObject(engine, 0);
            //获取注册中心的引用
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 6089);
            System.out.println("注册中");
            registry.rebind(name, skeleton);

            System.out.println("服务器运行中");

        }
        catch (Exception e){
            System.err.println("Exception:"+ e);
            e.printStackTrace();
        }
    }
}
