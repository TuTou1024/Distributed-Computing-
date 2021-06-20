package client;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

import bookInterface.Book;
import bookInterface.BookList;
import bookInterface.BookManage;

public class RMIClient {

	public static void title() {
		System.out.println("***********Books Management System***********");
		System.out.println("All the operations are:");
		System.out.println("#1 Add a new book");
		System.out.println("#2 Delete a book according to its BookID");
		System.out.println("#3 Query by BookID");
		System.out.println("#4 Query by BookName");
		System.out.println("#5 Show all the books");
		System.out.println("#6 exit");
		System.out.println("*********************************************");
		System.out.println("Please input the number of the corresponding operation:");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
			String name = "BookManagement";
            String serverIP = "127.0.0.1";//注册中心ip地址  
            int serverPort = 6089;//注册中心端口号
            //获取注册中心引用
            Registry registry = LocateRegistry.getRegistry(serverIP, serverPort);
			BookManage BM = (BookManage) registry.lookup(name);
			
			//BookManage BM = (BookManage)Naming.lookup("rmi://127.0.0.1:6089/BM");
			Scanner in = new Scanner(System.in);
			while(true) {
				title();
				int o = in.nextInt();
				if(o==1) {
					System.out.println("Input the new BookID and BookName you want to add:");
					int newID = in.nextInt();
					String newName = in.next();
					Book newB = new Book(newID,newName);
					if(BM.add(newB)) {
						System.out.println("Add successfully!");
					}else {
						System.out.println("Fail to add! The book "+newID+" has existed");
					}
				}
				
				else if(o==2) {
					System.out.println("Input the BookID you want to delete: ");
					int delID = in.nextInt();
					if(BM.delete(delID)) {
						System.out.println("The book "+delID+" has been deleted!");
					}else {
						System.out.println("Fail to delete!");
					}
				}
				
				else if(o==3) {
					System.out.println("Input the BookID you want to query:");
					int queryID = in.nextInt();
					if(BM.queryByID(queryID) == null) {
						System.out.println("There is no book ID is "+queryID);
					}else {
						Book b = BM.queryByID(queryID);
						b.showInfo();
					}
				}
				
				else if(o==4) {
					System.out.println("Input the BookName you want to query:");
					String queryName = in.next();
					BookList list = BM.queryByName(queryName);
					if(list != null) {
						list.showInfo();
					}else {
						System.out.println("There is no book Name is "+queryName);
					}
				}
				
				else if(o==5) {
					String show = BM.showAllBooks();
					System.out.println(show);
				}
				else if(o==6)	break;
				else {
					System.out.println("Please input again!");
				}
			}
		}catch(Exception e) {
			System.err.println("??? exception:");
			e.printStackTrace();
		}
	}

}
