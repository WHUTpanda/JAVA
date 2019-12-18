package whut.brms.Service;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whut.brms.Mapper.*;
import whut.brms.entity.Book;
import whut.brms.entity.Purchase;
import whut.brms.entity.Rent;

import java.util.Date;
import java.util.List;

@Service
public class BookService {
    @Autowired(required = false)
    BookMapper bookMapper;

    @Autowired(required = false)
    RentMapper rentMapper;

    @Autowired(required = false)
    PurchaseMapper purchaseMapper;

    @Autowired
    UserService userService;

    //方法名：showAll
    //功能：显示所有在库的书籍
    //参数：无
    //返回值：List<Book>
    public List<Book> showAll() {
        try {
            return bookMapper.showAll();
        } catch (Exception e) {
            return null;
        }
    }

    //方法名：RentBook
    //功能：租书
    //参数：用户名，模板书编号
    //返回值：1成功，2失败，3余额不足
    @Transactional
    public String RentBook(String User_ID, String Book_ID,int num) {
        try {
            Book book = bookMapper.queryBookById(Book_ID);
            float price = num * book.getBook_Price();
            if (book.getNum() >= num) {
                //未解决的bug，不支持pay函数回滚
                if (userService.pay(User_ID, price) == 2)//支付押金
                    return "3";//余额不足
                Date date = new Date(System.currentTimeMillis());
                //设置为请求状态
                String id = String.valueOf(System.currentTimeMillis());
                rentMapper.insertRent(id, User_ID, book.getBook_ID(), date, 1, num);//添加记录
                bookMapper.subBook(num, book.getBook_ID());
                return id;
            }
            return "4";
        } catch (Exception e) {
            return "2";
        }
    }

//
//    //方法名：PurchaseBook
//    //功能：买书
//    //参数：用户名，模板书编号,价格
//    //返回值：void
//    @Transactional
//    public void PurchaseBook(String User_ID, String ModelBook_ID)  {
//        List<Book> books=bookMapper.queryBooksByModelbookId(ModelBook_ID);
//        for(Book book:books)
//        {
//            if(book.getBook_Status()==2||book.getBook_Status()==3)
//            {
//                bookMapper.updateBookStatus(5,book.getBook_ID());
//                Date date=new Date(System.currentTimeMillis());
//                purchaseMapper.insertPurchase(book.getBook_ID(),User_ID,date,String.valueOf(System.currentTimeMillis()),1);
//                break;
//            }
//        }
//    }
//
//    //方法名：ReturnBook
//    //功能：归还书籍,被借出去的书籍
//    //参数：String类型：书编号Book_ID;String类型：用户编号User_ID
//    //返回值：boolean类型：成功为true，失败为false
//    @Transactional
//    public void ReturnBook(String Rent_ID){
//        Rent rent=rentMapper.queryRentById(Rent_ID);
//        String book_id=rent.getBook_ID();
//        bookMapper.updateBookStatus(1,book_id);
//        Date date=new Date(System.currentTimeMillis());
//        rentMapper.updateRentDate(date,Rent_ID);
//    }
//
    //方法名：Book
    //功能：通过Book_ID 查询书籍
    //参数：String类型：书编号Book_ID
    //返回值：book
    public Book SearchByBookId(String Book_ID) {
        return bookMapper.queryBookById(Book_ID);
    }

    //通过输入查找模板书
    public List<Book> SearchByInput(String input) {
        try {
            input="%"+input+"%";
            return bookMapper.searchByInput(input);
        }catch(Exception e){
            return null;
        }

    }
}
