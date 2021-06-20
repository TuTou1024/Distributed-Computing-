package BookInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Management extends Remote{

    //增加书
    boolean add(Book book) throws RemoteException;
    //按书号查询
    Book queryByID(int bookID) throws RemoteException;
    //按书名查询
    BookList queryByName(String bookName) throws RemoteException;
    //删除书
    boolean delete(int bookID) throws RemoteException;
    //展示所有书
    void showAllBooks() throws RemoteException;
}
