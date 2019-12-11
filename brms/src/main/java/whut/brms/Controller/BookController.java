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

    @PostMapping("/rent")
    @ResponseBody
    boolean rent(@RequestParam(value = "User_ID",required = true) String User_ID,
              @RequestParam(value = "ModelBook_ID",required = true) String ModelBook_ID)
    {
        try {
            bookService.RentBook(User_ID, ModelBook_ID);
        }catch (Exception e)
        {
            return false;
        }
        return true;
    }

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
    @PostMapping("/searchBookInput")
    @ResponseBody
    List<ModelBook> searchByInput(@RequestParam(value = "input",required = true) String Input)
    {
        return  bookService.SearchByInput(Input);
    }
    @PostMapping("/searchRentBook")
    @ResponseBody
    List<ModelBook> searchRentBook(@RequestParam(value = "Input",required = true) String Input)
    {
        return bookService.SearchByName_Rent(Input);
    }

    @PostMapping("/searchPurchaseBook")
    @ResponseBody
    List<ModelBook> searchPurchaseBook(@RequestParam(value = "Input",required = true) String Input)
    {

        return bookService.SearchByName_Purchase(Input);
    }

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
            bookService.AddBook(Book_Name,Book_Writer,Book_descrtption,Book_Price,Book_Status,num);
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }

    @PostMapping("/bookByModelId")
    @ResponseBody
    List<Book> searchBookByModelBookId(@RequestParam(value = "ModelBook_ID",required = true)String ModelBook_ID)
    {
        return bookService.SearchBookByModelBookID(ModelBook_ID);
    }

    @PostMapping("/queryRent")
    @ResponseBody
    List<Rent> queryRentRecord(@RequestParam(value = "User_ID")String User_ID)
    {
        return bookService.QueryRentRecord(User_ID);
    }

    @PostMapping("/queryPurchase")
    @ResponseBody
    List<Purchase> queryPurchaseRecord(@RequestParam(value = "User_ID")String User_ID)
    {
        return bookService.QueryPurchaseRecord(User_ID);
    }

    @PostMapping("/modelBookByModelId")
    @ResponseBody
    ModelBook searchModelBookByModelBookId(@RequestParam(value = "ModelBook_ID",required = true)String ModelBook_ID)
    {
        return bookService.SearchByModelBookID(ModelBook_ID);
    }

    @PostMapping("/bookByBookId")
    @ResponseBody
    Book searchBookByBookId(@RequestParam(value = "Book_ID",required = true)String Book_ID)
    {
        return bookService.SearchByBookId(Book_ID);
    }

}
