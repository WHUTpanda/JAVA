package whut.brms.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whut.brms.Mapper.BookMapper;
import whut.brms.Mapper.ModelBookMapper;
import whut.brms.Mapper.PurchaseMapper;
import whut.brms.Mapper.RentMapper;
import whut.brms.entity.Manager;
import whut.brms.entity.ModelBook;
import whut.brms.entity.Purchase;
import whut.brms.entity.Rent;

import java.util.ArrayList;
import java.util.Date;
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
    ModelBookMapper modelBookMapper;
    public List<Manager> showAll()
    {
        List<Rent> rents=rentMapper.queryRequesting();
        List<Purchase> purchases=purchaseMapper.queryRequesting();
        List<Manager> managers=new ArrayList<>();
        for(Rent rent:rents){
            Manager manager=new Manager();
            String bookId=rent.getBook_ID();
            String modelBookId=bookMapper.queryBookById(bookId).getModelBook_ID();
            manager.setBookName(modelBookMapper.queryModelBookById(modelBookId).getBook_Name());
            manager.setBookId(bookId);
            if(rent.getHandle()==1)
                manager.setHandle(1);
            else
                manager.setHandle(3);
            managers.add(manager);
        }
        for(Purchase purchase:purchases){
            Manager manager=new Manager();
            String bookId=purchase.getBook_ID();
            String modelBookId=bookMapper.queryBookById(bookId).getModelBook_ID();
            manager.setBookName(modelBookMapper.queryModelBookById(modelBookId).getBook_Name());
            manager.setBookId(bookId);
            managers.add(manager);
        }
        return managers;
    }

}
