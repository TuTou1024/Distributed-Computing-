package bookInterface;

import java.io.Serializable;
import java.util.ArrayList;

public class BookList implements Serializable{
	ArrayList<Book> booklist = new ArrayList<Book>();
	
	public String getInfo() {
		String info = "";
		for(int i=0;i<booklist.size();i++) {
			info +=("BookID: "+booklist.get(i).getID()+" BookName: "
					+booklist.get(i).getName()+" \n");
		}
		return info;
	}
	public void add_booklist(Book b) {
		this.booklist.add(b);
	}
	public void showInfo() {
		System.out.println(getInfo());
	}
}
