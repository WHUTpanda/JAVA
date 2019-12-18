package whut.brms.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whut.brms.Mapper.BookMapper;
import whut.brms.Mapper.PurchaseMapper;
import whut.brms.Mapper.RentMapper;
import whut.brms.entity.Book;
import whut.brms.entity.Purchase;
import whut.brms.entity.Rent;

import java.util.List;

@Service
public class RecordService {
    @Autowired
    RentMapper rentMapper;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    PurchaseMapper purchaseMapper;
    //方法名：QueryRentRecord
    //功能：查询当前用户借书记录
    //参数：String类型：用户名User_ID
    //返回值：List<Rent>：记录
    public List<Rent> QueryRentRecord(String User_ID) {
        List<Rent> rc = rentMapper.queryRentByUserId(User_ID);
        for (Rent rent:rc) {
            String Book_ID = rent.getBook_ID();
            Book book = bookMapper.queryBookById(Book_ID);
            rent.setBook_name(book.getBook_Name());
        }
        return rc;
    }
    //方法名：QueryPurchaseRecord
    //功能：查询当前用户借书记录
    //参数：String类型：用户名User_ID
    //返回值：List<Purchase>：记录
    public List<Purchase> QueryPurchaseRecord(String User_ID)  {
        List<Purchase> rc = purchaseMapper.queryPurchaseByUserId(User_ID);
        for (Purchase purchase:rc) {
            String Book_ID = purchase.getBook_ID();
            Book book = bookMapper.queryBookById(Book_ID);
            String book_name = book.getBook_Name();
            float book_Price = book.getBook_Price();
            purchase.setBook_name(book_name);
            purchase.setBook_price(book_Price);
        }
        return rc;
    }

}
