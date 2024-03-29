package whut.brms.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whut.brms.Mapper.BookMapper;
import whut.brms.Mapper.PurchaseMapper;
import whut.brms.Mapper.RentMapper;
import whut.brms.Mapper.UserMapper;
import whut.brms.entity.Book;
import whut.brms.entity.Manager;
import whut.brms.entity.Purchase;
import whut.brms.entity.Rent;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Service
public class ManagerService {
    @Autowired
    RentMapper rentMapper;
    @Autowired
    PurchaseMapper purchaseMapper;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    public List<Manager> showAll()
    {
        List<Rent> rents=rentMapper.queryRequesting();
        List<Purchase> purchases=purchaseMapper.queryRequesting();
        List<Manager> managers=new ArrayList<>();
        for(Rent rent:rents){
            Manager manager=new Manager();
            manager.setId(rent.getRent_ID());
            String bookId=rent.getBook_ID();
            Book book =bookMapper.queryBookById(bookId);
            manager.setBookName(book.getBook_Name());
            manager.setBookId(bookId);
            manager.setUserId(rent.getUser_ID());
            if(rent.getHandle()==1)
                manager.setHandle(1);
            else
                manager.setHandle(3);
            manager.setNum(rent.getNum());
            managers.add(manager);
        }
        for(Purchase purchase:purchases){
            Manager manager=new Manager();
            manager.setId(purchase.getPurchase_ID());
            String bookId=purchase.getBook_ID();
            Book book=bookMapper.queryBookById(bookId);
            manager.setBookName(book.getBook_Name());
            manager.setBookId(bookId);
            manager.setUserId(purchase.getUser_ID());
            manager.setHandle(2);
            manager.setNum(purchase.getNum());
            managers.add(manager);
        }
        return managers;
    }

    /**
     * 点击确定之后
     * @param Id
     * @param handle
     */
    @Transactional
    public boolean define(String Id,int handle,int num) {
        try {
            if (handle == 1)//租借请求
            {
                rentMapper.done(Id);
            } else if (handle == 2) {//购买请求
                purchaseMapper.done(Id);
            } else {//归还请求
                Rent rent = rentMapper.queryRentById(Id);
                Date date = new Date(System.currentTimeMillis());
                if (rent.getNum() == num) {
                    if(bookService.payRent(Id)) {
                        bookMapper.addBook(num, rent.getBook_ID());//库中添加书籍
                        rentMapper.updateRentDate(date, Id);//更新归还书籍
                    }
                } else if (rent.getNum() > num && num > 0) {
                    if(bookService.payRent(Id)) {
                        bookMapper.addBook(num, rent.getBook_ID());//库中添加书籍
                    }
                }
                rentMapper.done(Id);
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 搜索订单
     * String input
     * @param input
     * @return
     */
    public List<Manager> searchManager(String input){
        input="%"+input+"%";
        List<Rent> rents=rentMapper.searchRent(input);
        List<Purchase> purchases=purchaseMapper.searchPurchase(input);
        List<Manager> managers=new ArrayList<>();
        for(Rent rent:rents){
            Manager manager=new Manager();
            manager.setId(rent.getRent_ID());
            String bookId=rent.getBook_ID();
            Book book =bookMapper.queryBookById(bookId);
            manager.setBookName(book.getBook_Name());
            manager.setBookId(bookId);
            manager.setUserId(rent.getUser_ID());
            if(rent.getHandle()==1)
                manager.setHandle(1);
            else
                manager.setHandle(3);
            manager.setNum(rent.getNum());
            managers.add(manager);
        }
        for(Purchase purchase:purchases){
            Manager manager=new Manager();
            manager.setId(purchase.getPurchase_ID());
            String bookId=purchase.getBook_ID();
            Book book=bookMapper.queryBookById(bookId);
            manager.setBookName(book.getBook_Name());
            manager.setBookId(bookId);
            manager.setUserId(purchase.getUser_ID());
            manager.setHandle(2);
            manager.setNum(purchase.getNum());
            managers.add(manager);
        }
        return managers;


    }
    public int recharge(String User_ID,float money){
        if(userMapper.queryUserById(User_ID)!=null)
        {
            if(userService.recharge(User_ID,money))
                return 1;
            else
                return 0;
        }
        return 2;
    }

}
