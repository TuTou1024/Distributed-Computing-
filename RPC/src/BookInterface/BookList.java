package BookInterface;

import java.io.Serializable;
import java.util.ArrayList;

public class BookList implements Serializable{
    //创建图书列表
    ArrayList<Book> books = new ArrayList<Book>();

    //添加图书
    public void add_Booklist(Book book){
        this.books.add(book);
    }
    public void showInfo(){
        String info = "";
        for (int i=0;i<books.size();i++){
            info += ("书号为:"+ books.get(i).getBookID() + "书名为:" + books.get(i).getBookName() + "\n");
        }
        System.out.println(info);
    }


}
