package bookInterface;

import java.io.Serializable;

public class Book implements Serializable{
	private int bookID;
	private String bookName;
	
	public Book(int ID,String name) {
		this.bookID = ID;
		this.bookName = name;
	}
	public int getID() {
		return bookID;
	}
	public String getName() {
		return bookName;
	}
	public String getInfo() {
		return "BookID: "+bookID+" BookName: "+bookName+" \n";
	}
	public void showInfo() {
		System.out.println(getInfo());
	}
}
