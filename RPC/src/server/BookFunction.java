package server;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import BookInterface.Book;
import BookInterface.BookList;
import BookInterface.Management;

public class BookFunction implements Management{
    BookFunction() throws RemoteException{
        super();
    }

    ArrayList<Book> Books = new ArrayList<>();


    @Override
    public boolean add(Book book) throws RemoteException {
        for (int i=0;i<Books.size();i++){
            if (Books.get(i).getBookID()==book.getBookID()){
                return false;
            }
        }
        Books.add(book);
        return true;
    }

    @Override
    public Book queryByID(int bookID) throws RemoteException {
        for (int i=0;i<Books.size();i++){
            if (Books.get(i).getBookID() == bookID){
                Book book = Books.get(i);
                return book;
            }
        }
        return null;
    }

    @Override
    public BookList queryByName(String bookName) throws RemoteException {
        BookList Name = new BookList();
        for (int i=0;i<Books.size();i++){
            if (Books.get(i).getBookName().indexOf(bookName)!=-1){
                Book book = Books.get(i);
                Name.add_Booklist(book);
            }
        }
        return Name;
    }

    @Override
    public boolean delete(int bookID) throws RemoteException {
        for (int i=0;i<Books.size();i++){
            if (Books.get(i).getBookID() == bookID){
                Books.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void showAllBooks() throws RemoteException {
        String info = "";
        for(int i = 0;i<Books.size();i++) {
            info += ("书号: "+ Books.get(i).getBookID()+" 书名: "+Books.get(i).getBookName()+"\n");
        }
        System.out.println(info);
    }
}
