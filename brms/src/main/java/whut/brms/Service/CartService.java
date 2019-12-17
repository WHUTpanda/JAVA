package whut.brms.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whut.brms.Mapper.CartMapper;
import whut.brms.entity.ShoppingCart;

import java.util.Date;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CartMapper cartMapper;
    @Autowired
    BookService bookService;
    /**
     * 添加购物车
     */
    @Transactional
    public boolean addCart(String userId,String bookId,boolean type, float price)
    {
        try{
            Date date=new Date(System.currentTimeMillis());
            cartMapper.insertCart(String.valueOf(System.currentTimeMillis()),bookId,userId,date,type,price,false);
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
         return cartMapper.queryUserCart(userId);
        }catch (Exception e)
        {
            return null;
        }
    }


}
