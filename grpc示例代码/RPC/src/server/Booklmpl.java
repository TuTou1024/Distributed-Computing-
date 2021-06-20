package server;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import bookInterface.BookManage;
import bookInterface.Book;
import bookInterface.BookList;

public class Booklmpl implements BookManage {

	Booklmpl()throws RemoteException{
		super();
	}
	
	ArrayList<Book> All_books = new  ArrayList<Book>();

	@Override
	public boolean delete(int bookID) throws RemoteException {
		// TODO Auto-generated method stub
		for(int i=0;i<All_books.size();i++) {
			if(All_books.get(i).getID() == bookID) {
				All_books.remove(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean add(Book b) throws RemoteException {
		// TODO Auto-generated method stub
		for(int i = 0;i<All_books.size();i++) {
			if(All_books.get(i).getID() == b.getID()) {
				return false;
			}
		}
		All_books.add(b);
		return true;
	}

	@Override
	public Book queryByID(int bookID) throws RemoteException {
		// TODO Auto-generated method stub
		for(int i = 0;i<All_books.size();i++) {
			if(All_books.get(i).getID() == bookID) {
				Book b = All_books.get(i);
				return b;
			}
		}
		return null;
	}

	@Override
	public BookList queryByName(String name) throws RemoteException {
		// TODO Auto-generated method stub
		//ArrayList<Book> query = new ArrayList<Book>();
		BookList queryName = new BookList();
		for(int i = 0;i<All_books.size();i++) {
			if(All_books.get(i).getName().indexOf(name)!=-1) {
				Book b = All_books.get(i);
				queryName.add_booklist(b);
			}
		}
		return queryName;
	}
	
	@Override
	public String showAllBooks()throws RemoteException {
		String info = "";
		for(int i = 0;i<All_books.size();i++) {
			info += ("BookID: "+ All_books.get(i).getID()+" BookName: "+All_books.get(i).getName()+"\n");
		}
		return info;
	}
}