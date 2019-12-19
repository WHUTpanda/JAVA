package whut.brms.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whut.brms.Mapper.BookMapper;
import whut.brms.Mapper.CartMapper;
import whut.brms.entity.Book;
import whut.brms.entity.ShoppingCart;

import java.util.Date;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CartMapper cartMapper;
    @Autowired
    BookService bookService;
    @Autowired
    BookMapper bookMapper;
    /**
     * 添加购物车
     */
    @Transactional
    public boolean addCart(String userId,String bookId,int num)
    {
        try{
            Date date=new Date(System.currentTimeMillis());
            cartMapper.insertCart(String.valueOf(System.currentTimeMillis()),bookId,userId,date,num);
            return  true;
        }catch (Exception e)
        {
            return false;
        }
    }
    /**
     * 移出购物车
     */
    @Transactional
    public boolean removeCart(String Cart_ID)
    {
        try{
            cartMapper.delCart(Cart_ID);
            return  true;
        }catch (Exception e)
        {
            return false;
        }
    }
    /**
     * 查询用户购物车
     */
    public List<ShoppingCart> queryCart(String userId)
    {
        try {
            List<ShoppingCart> shoppingCarts=cartMapper.queryUserCart(userId);
            for(ShoppingCart shoppingCart:shoppingCarts){
                Book book=bookMapper.queryBookById(shoppingCart.getBookId());
                shoppingCart.setBookName(book.getBook_Name());
                shoppingCart.setBookWriter(book.getBook_Writer());
                shoppingCart.setPrice(book.getBook_Price());
            }
            return shoppingCarts;
        }catch (Exception e)
        {
            return null;
        }
    }


}
