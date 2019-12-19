package whut.brms.Service;

import org.apache.catalina.User;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whut.brms.Mapper.*;
import whut.brms.entity.Book;
import whut.brms.entity.Purchase;
import whut.brms.entity.Rent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public synchronized String RentBook(String User_ID, String Book_ID,int num) {
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
                rentMapper.insertRent(id, User_ID, Book_ID, date, 1, num);//添加记录
                bookMapper.subBook(num, Book_ID);
                id=id.substring(8);
                return id;
            }
            return "4";
        } catch (Exception e) {
            return "2";
        }
    }


    //方法名：PurchaseBook
    //功能：买书
    //参数：用户名，模板书编号,价格
    //返回值：void
    @Transactional
    public synchronized String  PurchaseBook(String User_ID, String Book_ID,int num)  {
        try {
            Book book = bookMapper.queryBookById(Book_ID);
            float price = num * book.getBook_Price();
            if (book.getNum() >= num) {
                if (userService.pay(User_ID, price) == 2)
                    return "3";//余额不足
                Date date = new Date(System.currentTimeMillis());
                String id = String.valueOf(System.currentTimeMillis());
                purchaseMapper.insertPurchase(Book_ID, User_ID, date, id, 1, num);
                bookMapper.subBook(num, Book_ID);
                id=id.substring(8);
                return id;//成功
            }
            return "4";//库存不足
        }catch (Exception e)
        {
            return "2";//数据库出错
        }
    }

    //方法名：ReturnBook
    //功能：归还书籍,被借出去的书籍
    //参数：String类型：书编号Book_ID;String类型：用户编号User_ID
    //返回值：boolean类型：成功为true，失败为false
    @Transactional
    public void ReturnBook(String Rent_ID,int num){
        rentMapper.handleReturn(Rent_ID);
    }

    //方法名：Book
    //功能：通过Book_ID 查询书籍
    //参数：String类型：书编号Book_ID
    //返回值：book
    public Book SearchByBookId(String Book_ID) {
        return bookMapper.queryBookById(Book_ID);
    }

    //通过输入查找书
    public List<Book> SearchByInput(String input) {
        try {
            input="%"+input+"%";
            return bookMapper.searchByInput(input);
        }catch(Exception e){
            return null;
        }

    }

    /**
     * 支付租金
     * @param Rent_ID
     * @return
     */
    @Transactional
    public boolean payRent(String Rent_ID) {
        try {
            Rent rent = rentMapper.queryRentById(Rent_ID);
            float deposit = rent.getNum() * bookMapper.queryBookById(rent.getBook_ID()).getBook_Price();
            userService.recharge(rent.getUser_ID(), deposit);//退还押金
            //获取租借天数
            float days = (float) (rent.getReturn_Date().getTime() - rent.getRent_Date().getTime() + 1000000) / (60 * 60 * 24 * 1000);
            days=Math.min(100f,days);//超过100天按照一百天算
            //计算租借费用
            float rentMoney = days * deposit * 0.01f;
            userService.pay(rent.getUser_ID(), rentMoney);
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }
}
