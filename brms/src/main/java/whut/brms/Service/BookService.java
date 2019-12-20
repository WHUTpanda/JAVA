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
import java.util.ArrayList;
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

    @Autowired
    BookService bookService;
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
    //功能：租若干本书
    //参数：String User_ID, String[] Book_ID,int[] num
    //返回值：1成功，2失败，3余额不足,List有书库存不足,返回String类型则为租书号
    @Transactional
    public synchronized Object RentBook(String User_ID, String[] Book_ID,int[] num) {
        try {
            Book []book=new Book[Book_ID.length];
            List<String> bookNames=new ArrayList<>();
            bookNames.add("4");
            float sum=0;
            for (int i=0;i<Book_ID.length;i++) {
                book[i]=bookMapper.queryBookById(Book_ID[i]);
                if(num[i]<=book[i].getNum())
                    sum+=book[i].getBook_Price()*num[i];
                else
                    bookNames.add(book[i].getBook_Name());
            }
            if(bookNames.size()>1)//有书籍库存不足
                return bookNames;
            //未解决的bug，不支持pay函数回滚
            if (userService.pay(User_ID, sum) == 2)//支付押金
                return 3;//余额不足
            String id = null;
            for (int i=0;i<Book_ID.length;i++) {
                    Date date = new Date(System.currentTimeMillis());
                    //设置为请求状态
                    id = String.valueOf(System.currentTimeMillis());
                    rentMapper.insertRent(id, User_ID, Book_ID[i], date, 1, num[i]);//添加记录
                    bookMapper.subBook(num[i], Book_ID[i]);
                }
            id = id.substring(3,9);
            return id;
        } catch (Exception e) {
            return 2;
        }
    }

    //方法名：PurchaseBook
    //功能：买若干本书
    //参数：String User_ID, String[] Book_ID,int[] num
    //返回值：1成功，2失败，3余额不足,{"4",ID}id为ID的书库存不足,返回String类型则为购书号
    @Transactional
    public synchronized Object PurchaseBook(String User_ID, String[] Book_ID,int[] num) {
        try {
            Book []book=new Book[Book_ID.length];
            List<String> bookName=new ArrayList<>();
            float sum=0;
            bookName.add("4");
            for (int i=0;i<Book_ID.length;i++) {
                book[i]=bookMapper.queryBookById(Book_ID[i]);
                if(num[i]<=book[i].getNum())
                    sum+=book[i].getBook_Price()*num[i];
                else
                    bookName.add(book[i].getBook_Name());
            }
            if(bookName.size()>1)//有书籍库存不足
                return bookName;
            //未解决的bug，不支持pay函数回滚
            if (userService.pay(User_ID, sum) == 2)//支付押金
                return 3;//余额不足
            String id = null;
            for (int i=0;i<Book_ID.length;i++) {
                Date date = new Date(System.currentTimeMillis());
                //设置为请求状态
                id = String.valueOf(System.currentTimeMillis());
                purchaseMapper.insertPurchase(Book_ID[i],User_ID,date,id,1,num[i]);//添加记录
                bookMapper.subBook(num[i], Book_ID[i]);
            }
            id = id.substring(3,9);
            return id;
        } catch (Exception e) {
            return 2;
        }
    }

    //方法名：ReturnBook
    //功能：归还书籍,被借出去的书籍
    //参数：String类型：书编号Book_ID;String类型：用户编号User_ID
    //返回值：boolean类型：成功为true，失败为false
    @Transactional
    public boolean ReturnBook(String Rent_ID,int num){
        try
        {
            Rent rent=rentMapper.queryRentById(Rent_ID);
            if(rent.getNum()==num) {
                rentMapper.handleReturn(Rent_ID);//设置归还请求中
            }
            else if(rent.getNum()>num&&num>0)
            {
                rentMapper.subRentNum(num, Rent_ID);//减少原记录中的数目
                rentMapper.insertRent(String.valueOf(System.currentTimeMillis()),
                        rent.getUser_ID(), rent.getBook_ID(), rent.getRent_Date(),
                    2, num);//生成新的记录
            }
            return true;
        }catch (Exception e){
            return false;
        }
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
            float days = (float) (System.currentTimeMillis() - rent.getRent_Date().getTime() + 1000000) / (60 * 60 * 24 * 1000);
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
