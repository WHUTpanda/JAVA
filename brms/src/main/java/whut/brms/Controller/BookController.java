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
    List<Book> showAll()
    {
        return bookService.showAll();
    }

    //借书
    @PostMapping("/rent")
    @ResponseBody
    String rent(@RequestParam(value = "User_ID",required = true) String User_ID,
              @RequestParam(value = "Book_ID",required = true) String Book_ID,
                @RequestParam(value = "num",required = true) int num)
    {
        try {
            return bookService.RentBook(User_ID, Book_ID,num);
        }catch (Exception e)
        {
            return null;
        }
    }
    //买书
    @PostMapping("/purchase")
    @ResponseBody
    String purchase(@RequestParam(value = "User_ID",required = true) String User_ID,
    @RequestParam(value = "Book_ID",required = true) String ModelBook_ID,
    @RequestParam(value = "num",required = true) int num)
    {
        try{
            return bookService.PurchaseBook(User_ID,ModelBook_ID,num);
        }
        catch (Exception e){
            return null;
        }
    }
//    //还书
//    @PostMapping("/returnBook")
//    @ResponseBody
//    boolean returnBook(@RequestParam(value = "Rent_ID",required = true) String Rent_ID)
//    {
//        try{
//            bookService.ReturnBook(Rent_ID);
//            return true;
//        }catch (Exception e)
//        {
//            return false;
//        }
//    }
    //通过搜索内容进行搜索全部书籍
    @PostMapping("/searchBookInput")
    @ResponseBody
    List<Book> searchByInput(@RequestParam(value = "input",required = true) String Input)
    {
        return  bookService.SearchByInput(Input);
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
    //通过d查找书
    @PostMapping("/byBookId")
    @ResponseBody
    Book searchBookById(@RequestParam(value = "Book_ID",required = true)String Book_ID)
    {
        return bookService.SearchByBookId(Book_ID);
    }

}
