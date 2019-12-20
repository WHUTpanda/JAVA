package whut.brms.Controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import whut.brms.Service.BookService;
import whut.brms.Service.CartService;
import whut.brms.entity.ShoppingCart;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController  {
    @Autowired
    CartService cartService;
    @Autowired
    BookService bookService;
    @PostMapping("/show")
    @ResponseBody
    public List<ShoppingCart> showCart(@RequestParam(value = "User_ID",required = true) String userId)
    {
        return cartService.queryCart(userId);
    }

    @PostMapping("/add")
    @ResponseBody
    public boolean addCart(@RequestParam(value = "User_ID",required = true) String userId,
                           @RequestParam(value = "Book_ID",required = true) String bookId,
                           @RequestParam(value = "num",required = true) int num)
    {
        return cartService.addCart(userId,bookId,num);
    }

    @PostMapping("/remove")
    @ResponseBody
    public boolean removeCart(@RequestParam(value = "cartId",required = true)String[] cartId)
    {
        return cartService.removeCart(cartId);
    }

    @PostMapping("/rent")
    @ResponseBody
    public Object rent(@RequestParam(value = "Cart_ID",required = true) String[] Cart_ID,
                       @RequestParam(value = "User_ID",required = true) String User_ID,
                       @RequestParam(value = "Book_ID",required = true) String[] bookId,
                       @RequestParam(value = "num",required = true) int[] num) {
        try {
            Object object= bookService.RentBook(User_ID, bookId, num);
            if (!cartService.removeCart(Cart_ID))
                return false;
            return object;
        }
        catch (Exception e){
            return false;
        }
    }
    @PostMapping("/purchase")
    @ResponseBody
    public Object purchase(@RequestParam(value = "Cart_ID",required = true) String[] Cart_ID,
                       @RequestParam(value = "User_ID",required = true) String User_ID,
                       @RequestParam(value = "Book_ID",required = true) String[] bookId,
                       @RequestParam(value = "num",required = true) int[] num) {
        try {
            Object object= bookService.PurchaseBook(User_ID, bookId, num);
            if (!cartService.removeCart(Cart_ID))
                return false;
            return object;
        }
        catch (Exception e){
            return false;
        }
    }
}
