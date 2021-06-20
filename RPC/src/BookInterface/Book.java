package BookInterface;

import java.io.Serializable;

public class Book implements Serializable{
    //定义书号和书名
    private int BookID;
    private String BookName;

    public Book(int id, String name){
        this.BookID = id;
        this.BookName = name;
    }

    //定义相关函数
    public int getBookID(){
        return BookID;
    }
    public String getBookName(){
        return BookName;
    }
    public void showInfo(){
        System.out.println("书号为:" + BookID +" || "+ "书名为:" + BookName + "\n");
    }
}
