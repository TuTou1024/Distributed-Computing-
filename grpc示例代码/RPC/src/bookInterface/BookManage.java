package bookInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BookManage extends Remote {
	
	boolean add(Book b) throws RemoteException;
	//��ѯָ��ID�ŵ��鼮����
	Book queryByID(int bookID) throws RemoteException;
	//��������ѯ�����������鼮�����б�֧�ֲ����ַ���ƥ��
	BookList queryByName(String name)throws RemoteException;
	boolean delete(int bookID)throws RemoteException;
	String showAllBooks() throws RemoteException;
}
