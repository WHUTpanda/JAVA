package whut.brms.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import whut.brms.Service.BookService;
import whut.brms.entity.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;
    //显示全部书籍
    @RequestMapping("/show")
    @ResponseBody
    List<ModelBook> showAll()
    {
        return bookService.showAll();
    }

    //显示所有可租借书籍
    @RequestMapping("/showRent")
    @ResponseBody
    List<ModelBook> showAllRent()
    {
        try {
            return bookService.ShowAllRent();
        }catch (Exception e)
        {
            return null;
        }

    }
    //显示所有可购买书籍
    @RequestMapping("/showPurchase")
    @ResponseBody
    List<ModelBook> showAllPurchase()
    {
        try {
            return bookService.ShowAllPurchase();
        }catch (Exception e)
        {
            return null;
        }

    }
    //借书
    @PostMapping("/rent")
    @ResponseBody
    String rent(@RequestParam(value = "User_ID",required = true) String User_ID,
              @RequestParam(value = "ModelBook_ID",required = true) String ModelBook_ID)
    {
        try {
            return bookService.RentBook(User_ID, ModelBook_ID);//
        }catch (Exception e)
        {
            return null;
        }
    }
    //买书
    @PostMapping("/purchase")
    @ResponseBody
    boolean purchase(@RequestParam(value = "User_ID",required = true) String User_ID,
    @RequestParam(value = "ModelBook_ID",required = true) String ModelBook_ID)
    {
        try{
            bookService.PurchaseBook(User_ID,ModelBook_ID);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    //还书
    @PostMapping("/returnBook")
    @ResponseBody
    boolean returnBook(@RequestParam(value = "Rent_ID",required = true) String Rent_ID)
    {
        try{
            bookService.ReturnBook(Rent_ID);
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }
    //通过搜索内容进行搜索全部书籍
    @PostMapping("/searchBookInput")
    @ResponseBody
    List<ModelBook> searchByInput(@RequestParam(value = "input",required = true) String Input)
    {
        return  bookService.SearchByInput(Input);
    }

    //通过搜索内容搜索可租借书籍
    @PostMapping("/searchRentBook")
    @ResponseBody
    List<ModelBook> searchRentBook(@RequestParam(value = "Input",required = true) String Input)
    {
        return bookService.SearchByName_Rent(Input);
    }
    //通过搜索内容搜索购买书籍
    @PostMapping("/searchPurchaseBook")
    @ResponseBody
    List<ModelBook> searchPurchaseBook(@RequestParam(value = "Input",required = true) String Input)
    {

        return bookService.SearchByName_Purchase(Input);
    }
    //添加书籍
    @PostMapping("/add")
    @ResponseBody
    boolean add(@RequestParam(value = "Book_Name",required = true) String Book_Name,
                @RequestParam(value = "Book_Writer",required = true) String Book_Writer,
                @RequestParam(value = "Book_descrtption",required = true) String Book_descrtption,
                @RequestParam(value = "Book_Price",required = true) float Book_Price,
                @RequestParam(value = "Book_Status",required = true) int Book_Status,
                @RequestParam(value = "num",required = true) int num)
    {
        try{
          //  bookService.AddBook(Book_Name,Book_Writer,Book_descrtption,Book_Price,Book_Status,num);
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }
    //通过模板书id进行查找个体书
    @PostMapping("/bookByModelId")
    @ResponseBody
    List<Book> searchBookByModelBookId(@RequestParam(value = "ModelBook_ID",required = true)String ModelBook_ID)
    {
        return bookService.SearchBookByModelBookID(ModelBook_ID);
    }
    //查询租借书籍记录
    @PostMapping("/queryRent")
    @ResponseBody
    List<Rent> queryRentRecord(@RequestParam(value = "User_ID")String User_ID)
    {
        return bookService.QueryRentRecord(User_ID);
    }
    //查询购买书籍记录
    @PostMapping("/queryPurchase")
    @ResponseBody
    List<Purchase> queryPurchaseRecord(@RequestParam(value = "User_ID")String User_ID)
    {
        return bookService.QueryPurchaseRecord(User_ID);
    }
    //通过模板书id查询模板书
    @PostMapping("/modelBookByModelId")
    @ResponseBody
    ModelBook searchModelBookByModelBookId(@RequestParam(value = "ModelBook_ID",required = true)String ModelBook_ID)
    {
        return bookService.SearchByModelBookID(ModelBook_ID);
    }
    //通过个体书id查找个体书
    @PostMapping("/bookByBookId")
    @ResponseBody
    Book searchBookByBookId(@RequestParam(value = "Book_ID",required = true)String Book_ID)
    {
        return bookService.SearchByBookId(Book_ID);
    }

}
