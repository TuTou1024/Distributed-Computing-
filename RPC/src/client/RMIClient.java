package client;


import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

import BookInterface.Book;
import BookInterface.BookList;
import BookInterface.Management;

public class RMIClient {
    public static void main(String[] args){
        try{
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 6089);
            Management Mg = (Management) registry.lookup("Management");

            Scanner in = new Scanner(System.in);
            while (true){
                System.out.println("***********图书管理系统***********");
                System.out.println("指令如下:");
                System.out.println("#1 添加新书");
                System.out.println("#2 按书号查询");
                System.out.println("#3 按书名查询");
                System.out.println("#4 输入书号删除图书");
                System.out.println("#5 显示所有图书");
                System.out.println("#6 退出");
                System.out.println("*********************************************");
                System.out.println("请输入指令:");
                int i = in.nextInt();
                switch (i){
                    case 1:
                        System.out.println("请输入想要添加的书号和书名:");
                        int newID = in.nextInt();
                        String newName = in.next();
                        Book book = new Book(newID, newName);
                        if (Mg.add(book)){
                            System.out.println("添加成功!");
                            break;
                        }else {
                            System.out.println("添加失败! 书号 "+newID+" 已经存在");
                            break;
                        }
                    case 2:
                        System.out.println("请输入想要查询的书号:");
                        int id = in.nextInt();
                        if(Mg.queryByID(id)==null){
                            System.out.println("书号："+id+" 该书不存在!");
                            break;
                        }else{
                            Book book1 = Mg.queryByID(id);
                            book1.showInfo();
                            break;
                        }
                    case 3:

                        System.out.println("请输入想要查询的书名:");
                        String Name = in.next();
                        BookList bookList = Mg.queryByName(Name);
                        if(bookList != null) {
                            bookList.showInfo();
                            break;
                        }else {
                            System.out.println("书号："+Name+"该书不存在!");
                            break;
                        }
                    case 4:
                        System.out.println("请输入想要删除的书: ");
                        int delID = in.nextInt();
                        if(Mg.delete(delID)) {
                            System.out.println("书号: "+delID+" 已经被删除!");
                            break;
                        }else {
                            System.out.println("删除失败!");
                            break;
                        }
                    case 5:
                        Mg.showAllBooks();
                        break;
                    case 6:
                        System.out.println("请重新输入指令:");
                        break;
                }

            }
        }catch (Exception e){
            System.err.println("Exception:");
            e.printStackTrace();

        }




    }

}
