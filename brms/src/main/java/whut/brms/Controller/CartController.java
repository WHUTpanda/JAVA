package whut.brms.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import whut.brms.Service.CartService;
import whut.brms.entity.ShoppingCart;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController  {
    @Autowired
    CartService cartService;
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
                           @RequestParam(value = "Type",required = true) boolean type,
                           @RequestParam(value = "Price",required = true) float price)
    {
        return cartService.addCart(userId,bookId,type, price);
    }

    @PostMapping("/remove")
    @ResponseBody
    public boolean removeCart(@RequestParam(value = "Cart_ID",required = true)String cartId )
    {
        return cartService.removeCart(cartId);
    }

}
