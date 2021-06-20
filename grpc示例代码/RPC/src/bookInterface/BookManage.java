package bookInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BookManage extends Remote {
	
	boolean add(Book b) throws RemoteException;
	//查询指定ID号的书籍对象
	Book queryByID(int bookID) throws RemoteException;
	//按书名查询符合条件的书籍对象列表，支持部分字符串匹配
	BookList queryByName(String name)throws RemoteException;
	boolean delete(int bookID)throws RemoteException;
	String showAllBooks() throws RemoteException;
}
