package whut.brms.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whut.brms.Mapper.BookMapper;
import whut.brms.Mapper.ModelBookMapper;
import whut.brms.Mapper.PurchaseMapper;
import whut.brms.Mapper.RentMapper;
import whut.brms.entity.Book;
import whut.brms.entity.ModelBook;
import whut.brms.entity.Purchase;
import whut.brms.entity.Rent;

import java.util.Date;
import java.util.List;

@Service
public class BookService {
    @Autowired(required = false)
    BookMapper bookMapper;

    @Autowired(required = false)
    ModelBookMapper modelBookMapper;
    @Autowired(required = false)
    RentMapper rentMapper;
    @Autowired(required = false)
    PurchaseMapper purchaseMapper;

    //方法名：AddBook
    //功能：获取文本框中的内容来添加书籍
    //参数：String Book_Name,String Book_writer,String Book_description,Float Book_Price,int Book_Status,int num数目
    //返回值：boolean
    @Transactional//回滚注解
    public boolean AddBook(String Book_Name, String Book_writer, String Book_description, float Book_Price, int Book_Status, int num)  {
        ModelBook AddedRt = modelBookMapper.queryModelBookByWriterName(Book_Name, Book_writer);
        String ModelBook_ID;
        if (AddedRt == null) {
            String NowTime = String.valueOf(System.currentTimeMillis());
            modelBookMapper.insertModelBook(NowTime,Book_writer,Book_Name,Book_description,Book_Price);
            ModelBook_ID=NowTime;
        } else {
            ModelBook_ID=AddedRt.getModelBook_ID();
        }

        for (int i = 0; i < num; i++) {
            String nowTime = String.valueOf(System.currentTimeMillis());
            Date date = new Date(System.currentTimeMillis());
            bookMapper.insertBook(nowTime,Book_Status,date,ModelBook_ID);
        }
        return true;
    }

    //方法名：SearchByName_Rent
    //功能：在租借区按照书名查找
    //参数：String类型：Book_Name
    //返回值：List<ModelBook>
    public List<ModelBook> SearchByName_Rent(String Book_Name)  {
        try {
            Book_Name = "%" + Book_Name + "%";
            return modelBookMapper.queryRentModelBookByInput(Book_Name);
        }
        catch (Exception e)
        {
            System.out.println("数据库出错");
        }
        return null;
    }

    //方法名：SearchByName_Purchase
    //功能：在购买区按照书名查找
    //参数：String类型：Book_Name
    //返回值：List<ModelBook>
    public List<ModelBook> SearchByName_Purchase(String Book_Name) {
        Book_Name='%'+Book_Name+'%';
        return modelBookMapper.queryPurchaseModelBookByInput(Book_Name);
    }

    //方法名：ShowAllRent
    //功能：显示所有待租书
    //参数：无
    //返回值：List<ModelBook>
    public List<ModelBook> ShowAllRent() {
        return modelBookMapper.queryAllRent();
    }

    //方法名：ShowAllPurchase
    //功能：显示所有待售书
    //参数：无
    //返回值：List<ModelBook>
    public List<ModelBook> ShowAllPurchase() {
        return modelBookMapper.queryAllPurchase();
    }

    //方法名：RentBook
    //功能：租书
    //参数：用户名，模板书编号
    //返回值：void
    @Transactional
    public void RentBook(String User_ID, String ModelBook_ID) {
        List<Book> books=bookMapper.queryBooksByModelbookId(ModelBook_ID);
        for(Book book:books)
        {
            if(book.getBook_Status()==1||book.getBook_Status()==3)
            {
                bookMapper.updateBookStatus(4,book.getBook_ID());
                Date date=new Date(System.currentTimeMillis());
                rentMapper.insertRent(String.valueOf(System.currentTimeMillis()),User_ID,book.getBook_ID(),date);
                break;
            }
        }
    }

    //方法名：PurchaseBook
    //功能：买书
    //参数：用户名，模板书编号,价格
    //返回值：void
    @Transactional
    public void PurchaseBook(String User_ID, String ModelBook_ID)  {
        List<Book> books=bookMapper.queryBooksByModelbookId(ModelBook_ID);
        for(Book book:books)
        {
            if(book.getBook_Status()==2||book.getBook_Status()==3)
            {
                bookMapper.updateBookStatus(5,book.getBook_ID());
                Date date=new Date(System.currentTimeMillis());
                purchaseMapper.insertPurchase(book.getBook_ID(),User_ID,date);
                break;
            }
        }
    }

    //方法名：ReturnBook
    //功能：归还书籍,被借出去的书籍
    //参数：String类型：书编号Book_ID;String类型：用户编号User_ID
    //返回值：boolean类型：成功为true，失败为false
    @Transactional
    public void ReturnBook(String Rent_ID){
        Rent rent=rentMapper.queryRentById(Rent_ID);
        String book_id=rent.getBook_ID();
        bookMapper.updateBookStatus(1,book_id);
        Date date=new Date(System.currentTimeMillis());
        rentMapper.updateRentDate(date,Rent_ID);
    }

    //方法名：Book
    //功能：通过Book_ID 查询书籍
    //参数：String类型：书编号Book_ID
    //返回值：book
    public Book SearchByBookId(String Book_ID) {
        return bookMapper.queryBookById(Book_ID);
    }

    //方法名：QueryRentRecord
    //功能：查询当前用户借书记录
    //参数：String类型：用户名User_ID
    //返回值：List<Rent>：记录
    public List<Rent> QueryRentRecord(String User_ID) {
        List<Rent> rc = rentMapper.queryRentByUserId(User_ID);
        for (Rent rent:rc) {
            String Book_ID = rent.getBook_ID();
            String ModelBook_ID =bookMapper.queryBookById(Book_ID).getModelBook_ID();
            ModelBook modelBook = modelBookMapper.queryModelBookById(ModelBook_ID);
            String book_name = modelBook.getBook_Name();
            rent.setBook_name(book_name);
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
            String ModelBook_ID = bookMapper.queryBookById(Book_ID).getModelBook_ID();
            ModelBook modelBook = modelBookMapper.queryModelBookById(ModelBook_ID);
            String book_name = modelBook.getBook_Name();
            float book_Price = modelBook.getBook_Price();
            purchase.setBook_name(book_name);
            purchase.setBook_price(book_Price);
        }
        return rc;
    }

    //方法名：SearchByModelBookID
    //功能：通过模板书ID查找
    //参数：String类型：ModelBook_ID
    //返回值：ModelBook
    public ModelBook SearchByModelBookID(String ModelBook_ID)  {
        return modelBookMapper.queryModelBookById(ModelBook_ID);
    }

    //方法名：SearchBookByModelBookID
    //功能：通过模板书ID查找个体书
    //参数：String类型：ModelBook_ID
    //返回值：list<Book>
    public List<Book> SearchBookByModelBookID(String ModelBook_ID){
        return bookMapper.queryBooksByModelbookId(ModelBook_ID);
    }
    //通过输入查找模板书
    public List<ModelBook> SearchByInput(String input) {
        try {
            input="%"+input+"%";
            return modelBookMapper.queryPurchaseModelBookByInput(input);
        }catch(Exception e){
            System.out.println("访问数据库出错");
        }
        return null;
    }
}
