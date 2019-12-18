package whut.brms.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whut.brms.Mapper.BookMapper;
import whut.brms.Mapper.PurchaseMapper;
import whut.brms.Mapper.RentMapper;
import whut.brms.entity.Book;
import whut.brms.entity.Manager;
import whut.brms.entity.Purchase;
import whut.brms.entity.Rent;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {
    @Autowired
    RentMapper rentMapper;
    @Autowired
    PurchaseMapper purchaseMapper;
    @Autowired
    BookMapper bookMapper;

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
     * @param type
     */
    @Transactional
    public void define(String Id,int type)
    {
        if(type==1)
        {
            rentMapper.done(Id);
        }
        else if(type==2){
            purchaseMapper.done(Id);
        }
        else {
            rentMapper.done(Id);
        }
    }

}
